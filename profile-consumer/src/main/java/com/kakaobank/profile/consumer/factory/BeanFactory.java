package com.kakaobank.profile.consumer.factory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.consumer.component.MessageConverter;
import com.kakaobank.profile.consumer.controller.ReadAccountProfileController;
import com.kakaobank.profile.consumer.controller.ReadCustomerProfileController;
import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryAccountDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryCustomerDao;
import com.kakaobank.profile.consumer.service.ReadAccountProfileService;
import com.kakaobank.profile.consumer.service.ReadCustomerProfileService;
import com.kakaobank.profile.consumer.service.impl.ReadAccountProfileServiceImpl;
import com.kakaobank.profile.consumer.service.impl.ReadCustomerProfileServiceImpl;

public class BeanFactory {

    public static MessageConverter createMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }

    public static ReadCustomerProfileController createReadCustomerProfileController(ReadCustomerProfileService readCustomerProfileService, MessageConverter messageConverter) {

        return new ReadCustomerProfileController(readCustomerProfileService, messageConverter);
    }

    public static ReadCustomerProfileService createReadCustomerProfileService(CustomerDao customerDao, AccountDao accountDao) {
        return new ReadCustomerProfileServiceImpl(customerDao, accountDao);
    }

    public static ReadAccountProfileController createReadAccountProfileController(ReadAccountProfileService readAccountProfileService, MessageConverter messageConverter) {

        return new ReadAccountProfileController(readAccountProfileService, messageConverter);
    }

    public static ReadAccountProfileService createReadAccountProfileService(AccountDao accountDao) {
        return new ReadAccountProfileServiceImpl(accountDao);
    }

    public static CustomerDao createCustomerDao() {
        return new MemoryCustomerDao();
    }

    public static AccountDao createAccountDao() {
        return new MemoryAccountDao();
    }

}
