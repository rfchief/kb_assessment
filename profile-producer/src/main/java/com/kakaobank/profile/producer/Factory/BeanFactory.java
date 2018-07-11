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
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.service.ExecuteProcessService;
import com.kakaobank.profile.producer.service.WriteProfileService;
import com.kakaobank.profile.producer.service.impl.ExecuteProcessServiceImpl;
import com.kakaobank.profile.producer.service.impl.WriteProfileServiceImpl;
import com.kakaobank.profile.producer.worker.ProfileWorker;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class BeanFactory {

    public static MessageConverter createMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return new MessageConverter(objectMapper);
    }

    public static WriteDataToFileComponent createWriteDataToFileComponent(String filePath) {
        return new WriteDataToFileComponentImpl(filePath);
    }

    public static WriteDataToKafkaComponent createWriteDataToKafkaComponent(Properties configs, String kafkaTopic) {
        return new WriteDataToKafkaComponentImpl(new KafkaProducer<String, String>(configs), kafkaTopic);
    }

    public static AccountLogGenerator createAccountLogGenerator(int maxBoundForAmount) {
        return new AccountLogGenerator(maxBoundForAmount);
    }

    public static AccountLogGenerator createAccountLogGenerator() {
        return createAccountLogGenerator(1000000);
    }

    public static CustomerProfileGenerator createCustomerProfileGenerator() {
        return new CustomerProfileGenerator();
    }

    public static WriteProfileService createWriteProfileService(String filePath, String kafkaTopic, Properties kafkaConfigs) {
        return new WriteProfileServiceImpl(createWriteDataToFileComponent(filePath), createWriteDataToKafkaComponent(kafkaConfigs, kafkaTopic), createMessageConverter());
    }

    public static ProfileWorker createProfileWorker(Customer customer, int maxLogCount, String logFileName, String kafkaTopic, Properties kafkaConfigs) {
        return new ProfileWorker(customer, maxLogCount, createAccountLogGenerator(), createWriteProfileService(logFileName, kafkaTopic, kafkaConfigs));
    }

    public static Properties createKafkaConfigs(Properties properties) {
        String bootstrapServers = properties.getProperty("kafka.producer.bootstrap.servers");
        String acks = properties.getProperty("kafka.producer.acks");
        String compressionType = properties.getProperty("kafka.producer.compression.type");

        Properties kafkaConfigs = new Properties();
        kafkaConfigs.put("bootstrap.servers", bootstrapServers);
        kafkaConfigs.put("acks", acks);
        kafkaConfigs.put("compression.type", compressionType);
        kafkaConfigs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaConfigs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return kafkaConfigs;
    }

    public static ExecuteProcessService createExecuteProcessService() {
        return new ExecuteProcessServiceImpl(createCustomerProfileGenerator());
    }
}
