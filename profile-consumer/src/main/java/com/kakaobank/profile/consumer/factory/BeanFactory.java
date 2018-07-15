package com.kakaobank.profile.consumer.factory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.consumer.component.MessageConverter;
import com.kakaobank.profile.consumer.component.ReadStreamFromKafkaComponent;
import com.kakaobank.profile.consumer.controller.ReadAccountProfileController;
import com.kakaobank.profile.consumer.controller.ReadCustomerProfileController;
import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryAccountDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryCustomerDao;
import com.kakaobank.profile.consumer.service.ReadAccountProfileService;
import com.kakaobank.profile.consumer.service.ReadCustomerProfileService;
import com.kakaobank.profile.consumer.service.WriteAccountLogService;
import com.kakaobank.profile.consumer.service.impl.ReadAccountProfileServiceImpl;
import com.kakaobank.profile.consumer.service.impl.ReadCustomerProfileServiceImpl;
import com.kakaobank.profile.consumer.service.impl.WriteAccountLogServiceImpl;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class BeanFactory {

    public static MessageConverter createMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }

    public static ReadCustomerProfileController createReadCustomerProfileController(ReadCustomerProfileService readCustomerProfileService) {

        return new ReadCustomerProfileController(readCustomerProfileService, createMessageConverter());
    }

    public static ReadCustomerProfileService createReadCustomerProfileService(CustomerDao customerDao, AccountDao accountDao) {
        return new ReadCustomerProfileServiceImpl(customerDao, accountDao);
    }

    public static ReadAccountProfileController createReadAccountProfileController(ReadAccountProfileService readAccountProfileService) {

        return new ReadAccountProfileController(readAccountProfileService, createMessageConverter());
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

    public static WriteAccountLogService createWriteAccountLogService(CustomerDao customerDao, AccountDao accountDao) {
        return new WriteAccountLogServiceImpl(customerDao, accountDao, createMessageConverter());
    }

    public static ReadStreamFromKafkaComponent createReadStreamFromKafkaComponent(Properties kafkaConfigs, WriteAccountLogService writeAccountLogService) {
        return new ReadStreamFromKafkaComponent(new KafkaConsumer<String, String>(kafkaConfigs), writeAccountLogService);
    }

    public static Properties createKafkaConfigs(Properties properties) {
        String bootstrapServers = properties.getProperty("kafka.consumer.bootstrap.servers");
        String enableAutoCommit = properties.getProperty("kafka.consumer.enable.auto.commit");
        String autoOffsetReset = properties.getProperty("kafka.consumer.auto.offset.reset");
        String groupId = properties.getProperty("kafka.topic.name") + "_manually";

        Properties kafkaConfigs = new Properties();
        kafkaConfigs.put("bootstrap.servers", bootstrapServers);
        kafkaConfigs.put("enable.auto.commit", enableAutoCommit);
        kafkaConfigs.put("auto.offset.reset", autoOffsetReset);
        kafkaConfigs.put("group.id", groupId);
        kafkaConfigs.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConfigs.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return kafkaConfigs;
    }
}
