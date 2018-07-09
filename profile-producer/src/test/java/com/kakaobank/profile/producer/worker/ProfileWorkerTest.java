package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.component.MessageConverter;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.EventType;
import com.kakaobank.profile.producer.service.WriteProfileService;
import com.kakaobank.profile.producer.util.FileReader;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ProfileWorkerTest {

    private ProfileWorker worker;
    private Customer customer;
    private int maxLogCount;
    private String filePath = "logs/test.log";
    private MessageConverter messageConverter;

    @Before
    public void setup() throws NoSuchAlgorithmException, IOException {
        customer = TestDataFactory.getCustomer();
        maxLogCount = 100;
        AccountLogGenerator accountLogGenerator = TestDataFactory.getAccountLogGenerator();
        WriteProfileService writeProfileService = TestDataFactory.getWriteProfileService(filePath);
        this.messageConverter = TestDataFactory.getMessageConverter();
        this.worker = new ProfileWorker(customer, maxLogCount, accountLogGenerator, writeProfileService);
    }

    @After
    public void destroy() {
        new File(filePath).delete();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenEmptyCustomerAndMaxLogCount_whenCall_thenReturnZeroTest() throws Exception {
        //given
        ProfileWorker givenProfileWorker = new ProfileWorker(null, maxLogCount, null, null);

        //when
        int actual = givenProfileWorker.call();

        //then
        Assert.assertThat(actual, is(0));
    }

    @Test
    public void givenCustomerAndMaxLogCount_whenCall_thenReturnSameMaxLogCountTest() throws Exception {
        //given

        //when
        Integer actual = worker.call();

        //then
        Assert.assertThat(actual, is(maxLogCount + 2));
        List<String> contents = FileReader.readActualContent(filePath);
        Assert.assertThat(contents.size(), is(actual));
        for (String content : contents) {
            EventLog actualEventLog = messageConverter.read(content);
            assertJoinAndCreateEvent(actualEventLog);
            Assert.assertThat(actualEventLog.getCustomer(), is(notNullValue()));
        }
    }

    private void assertJoinAndCreateEvent(EventLog actualEventLog) {
        if(actualEventLog.getSeq() == 0)
            Assert.assertThat(actualEventLog.getEventType(), is(EventType.JOIN));
        if(actualEventLog.getSeq() == 1)
            Assert.assertThat(actualEventLog.getEventType(), is(EventType.CREATE));
    }

}
