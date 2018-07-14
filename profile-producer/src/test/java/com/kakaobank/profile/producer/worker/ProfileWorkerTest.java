package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;
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

public class ProfileWorkerTest {

    private ProfileWorker worker;
    private Customer customer;
    private int maxLogCount;
    private String filePath = "logs/test.log";
    private AccountLogGenerator accountLogGenerator;
    private WriteProfileService writeProfileService;

    @Before
    public void setup() throws NoSuchAlgorithmException, IOException {
        customer = TestDataFactory.getCustomer();
        maxLogCount = 100;
        accountLogGenerator = TestDataFactory.getAccountLogGenerator();
        writeProfileService = TestDataFactory.getWriteProfileService(filePath);
        this.worker = new ProfileWorker(customer, maxLogCount, accountLogGenerator, writeProfileService);
    }

    @After
    public void destroy() {
//        new File(filePath).delete();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyCustomerAndMaxLogCount_whenExecute_thenThrowIllegalArgumentExceptionTest() throws Exception {
        //given
        ProfileWorker givenProfileWorker = new ProfileWorker(null, maxLogCount, accountLogGenerator, writeProfileService);

        //when
        int actual = givenProfileWorker.execute();

        //then
        Assert.assertThat(actual, is(0));
    }

    @Test
    public void givenCustomerAndMaxLogCount_whenExecute_thenReturnSameMaxLogCountTest() throws Exception {
        //given

        //when
        Integer actual = worker.execute();

        //then
        Assert.assertThat(actual, is(maxLogCount + 2));
        List<String> contents = FileReader.readActualContent(filePath);
        Assert.assertThat(contents.size(), is(actual));
    }
}
