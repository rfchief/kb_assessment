package com.kakaobank.profile.producer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.producer.component.MessageConverter;
import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.service.WriteProfileService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

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
        return new WriteDataToFileComponent(filePath);
    }

    public static WriteProfileService getWriteProfileService(String filePath) throws IOException {
        return new WriteProfileService(getWriteDataToFileComponent(filePath), getMessageConverter());
    }

    public static EventLog getEventLog() throws NoSuchAlgorithmException {
        return getAccountLogGenerator().doGenerate(getCustomer());
    }

    public static MessageConverter getMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }
}
