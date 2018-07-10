package com.kakaobank.profile.producer.service;

import com.kakaobank.profile.producer.model.EventLog;
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

public class WriteProfileServiceTest {

    private WriteProfileService service;
    private String filePath;

    @Before
    public void setup() throws IOException, NoSuchAlgorithmException {
        this.filePath = "logs/test.log";
        this.service = TestDataFactory.getWriteProfileService(filePath);

        service.open();
    }

    @After
    public void destroy() throws IOException {
        File file = new File(filePath);
        file.delete();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullEventLog_whenWrite_thenThrowIllegalArgumentExceptionTest() {
        //given
        EventLog nullLog = null;

        //when
        boolean actual = service.write(nullLog);

        //then
        Assert.assertThat(actual, is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidEventLog_whenWrite_thenThrowIllegalArgumentExceptionTest() throws NoSuchAlgorithmException {
        //given
        EventLog invalidEventLog = TestDataFactory.getEventLog();
        invalidEventLog.setCustomer(null);

        //when
        boolean actual = service.write(invalidEventLog);

        //then
        Assert.assertThat(actual, is(true));
    }

    @Test
    public void givenEventLog_whenWrite_thenWriteJsonStringOfEventLogAtFileTest() throws NoSuchAlgorithmException, IOException {
        //given
        EventLog eventLog = TestDataFactory.getEventLog();

        //when
        boolean actual = service.write(eventLog);
        service.close();

        //then
        Assert.assertTrue(actual);
        List<String> contents = FileReader.readActualContent(filePath);
        Assert.assertThat(contents.size(), is(1));
    }
}
