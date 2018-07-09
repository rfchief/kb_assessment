package com.kakaobank.profile.producer.Factory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.producer.component.MessageConverter;
import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.service.WriteProfileService;

import java.io.IOException;

public class BeanFactory {

    public static MessageConverter createMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }

    public static WriteDataToFileComponent createWriteDataToFileComponent(String filePath) throws IOException {
        return new WriteDataToFileComponent(filePath);
    }

    public static AccountLogGenerator createAccountLogGenerator(int maxBoundForAmount) {
        return new AccountLogGenerator(maxBoundForAmount);
    }

    public static CustomerProfileGenerator createCustomerProfileGenerator() {
        return new CustomerProfileGenerator();
    }

    public static WriteProfileService createWriteProfileService(String filePath) throws IOException {
        return new WriteProfileService(createWriteDataToFileComponent(filePath));
    }

}
