package com.kakaobank.profile.producer.service;

import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.service.impl.ProducerServiceImpl;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class ProducerServiceIntegrationTest {

    private ProducerServiceImpl service;
    private Properties properties;
    private int maxThreadCount;
    private String filePath;

    @Before
    public void setup() {
        CustomerProfileGenerator customerGenerator = TestDataFactory.getCustomerProfileGenerator();
        properties = TestDataFactory.getProperties();
        maxThreadCount = 1;
        filePath = "logs/";
        this.service = new ProducerServiceImpl(customerGenerator);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyArguments_whenDoProcess_thenThrowsIllegalArgumentExceptionTest() {
        //given
        Properties emptyProperties = null;
        int maxThreadCount = 1;
        String emptyLogFilePath = "";

        //when
        service.doProcess(emptyProperties, maxThreadCount, emptyLogFilePath);
    }

    @Test
    public void givenMaxThreadCount_whenCreateThreadPool_thenReturnFixedThreadPoolTest() {
        //given
        int maxThreadCount = 3;

        //when
        service.doProcess(properties, maxThreadCount, "logs/files");

        //then

    }

}
