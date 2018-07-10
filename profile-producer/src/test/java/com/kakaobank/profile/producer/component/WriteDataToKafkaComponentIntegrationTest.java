package com.kakaobank.profile.producer.component;

import com.kakaobank.profile.producer.component.impl.WriteDataToKafkaComponentImpl;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class WriteDataToKafkaComponentIntegrationTest {

    private WriteDataToKafkaComponentImpl component;
    private String content;
    private String topic;

    @Before
    public void setup() throws NoSuchAlgorithmException, IOException {
        Properties props = getProperties();
        this.topic = "assessment";
        this.component = new WriteDataToKafkaComponentImpl(new KafkaProducer<String, String>(props), topic);
        this.content = TestDataFactory.getCustomer().toString();
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "1");
        properties.put("compression.type", "gzip");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return properties;
    }

    @After
    public void destroy() throws IOException {
        component.close();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyContent_whenWrite_thenThrowsIllegalArgumentExceptionTest() throws ExecutionException, InterruptedException {
        //given
        String emptyContent = null;

        //when
        component.write(emptyContent);
    }

    @Test
    public void givenFilePathAndContent_whenWrite_thenSendTest() throws ExecutionException, InterruptedException {
        //when
        component.write(content);
    }
}
