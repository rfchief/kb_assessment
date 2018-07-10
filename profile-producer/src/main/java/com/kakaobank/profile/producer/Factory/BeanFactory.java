package com.kakaobank.profile.producer.Factory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.producer.component.WriteDataToKafkaComponent;
import com.kakaobank.profile.producer.component.impl.MessageConverter;
import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.component.impl.WriteDataToFileComponentImpl;
import com.kakaobank.profile.producer.component.impl.WriteDataToKafkaComponentImpl;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.service.WriteProfileService;
import com.kakaobank.profile.producer.service.impl.WriteProfileServiceImpl;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.util.Properties;

public class BeanFactory {

    public static MessageConverter createMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }

    public static WriteDataToFileComponent createWriteDataToFileComponent(String filePath) throws IOException {
        return new WriteDataToFileComponentImpl(filePath);
    }

    public static WriteDataToKafkaComponent createWriteDataToKafkaComponent(Properties configs, String kafkaTopic) {
        return new WriteDataToKafkaComponentImpl(new KafkaProducer<String, String>(configs), kafkaTopic);
    }

    public static AccountLogGenerator createAccountLogGenerator(int maxBoundForAmount) {
        return new AccountLogGenerator(maxBoundForAmount);
    }

    public static CustomerProfileGenerator createCustomerProfileGenerator() {
        return new CustomerProfileGenerator();
    }

    public static WriteProfileService createWriteProfileService(String filePath, String kafkaTopic, Properties kafkaConfigs) throws IOException {
        return new WriteProfileServiceImpl(createWriteDataToFileComponent(filePath), createWriteDataToKafkaComponent(kafkaConfigs, kafkaTopic), createMessageConverter());
    }



}
