package com.kakaobank.profile.producer.service;

import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class WriteProfileServiceTest {

    private WriteProfileService service;

    @Before
    public void setup() throws IOException, NoSuchAlgorithmException {
        WriteDataToFileComponent writeDataToFileComponent = TestDataFactory.getWriteDataToFileComponent();
        this.service = new WriteProfileService(writeDataToFileComponent);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }
}
