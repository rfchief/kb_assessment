package com.kakaobank.profile.producer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.component.WriteDataToKafkaComponent;
import com.kakaobank.profile.producer.component.impl.MessageConverter;
import com.kakaobank.profile.producer.component.impl.WriteDataToFileComponentImpl;
import com.kakaobank.profile.producer.component.mock.MockWriteDataToKafkaComponent;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.service.WriteProfileService;
import com.kakaobank.profile.producer.service.impl.WriteProfileServiceImpl;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Properties;

public class TestDataFactory {

    public static Customer getCustomer() throws NoSuchAlgorithmException {
        LocalDateTime now = LocalDateTime.now();
        String name = StringUtil.randomString(10, true);
        String id = StringUtil.hash256(name + now.getNano());

        return new Customer(id, name, now);
    }

    public static CustomerProfileGenerator getCustomerProfileGenerator() {
        return new CustomerProfileGenerator();
    }

    public static AccountLogGenerator getAccountLogGenerator() {
        return getAccountLogGenerator(1000000);
    }

    public static AccountLogGenerator getAccountLogGenerator(int maxBoundForAmount) {
        return new AccountLogGenerator(maxBoundForAmount);
    }

    public static WriteDataToFileComponent getWriteDataToFileComponent(String filePath) throws IOException {
        return new WriteDataToFileComponentImpl(filePath);
    }

    public static WriteProfileService getWriteProfileService(String filePath) throws IOException {
        return new WriteProfileServiceImpl(getWriteDataToFileComponent(filePath), getWriteDataToKafkaComponent(), getMessageConverter());
    }

    public static WriteDataToKafkaComponent getWriteDataToKafkaComponent() {
        MockProducer<String, String> mockProducer = new MockProducer<String, String>(
                true, new StringSerializer(), new StringSerializer());
        return new MockWriteDataToKafkaComponent(mockProducer, "test");
    }

    public static EventLog getEventLog() throws NoSuchAlgorithmException {
        return getAccountLogGenerator().doGenerate(getCustomer());
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("kafka.producer.bootstrap.servers", "localhost:9092");
        properties.put("kafka.producer.acks", "1");
        properties.put("kafka.producer.compression.type", "gzip");
        properties.put("kafka.topic.name", "assessment");
        properties.put("log.max.count", "100");
        properties.put("customer.count", "10");
        properties.put("customer.name.max.length", "20");

        return properties;
    }

    public static MessageConverter getMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }
}
