package com.kakaobank.profile.consumer;

import com.kakaobank.profile.consumer.component.ReadStreamFromKafkaComponent;
import com.kakaobank.profile.consumer.controller.ReadAccountProfileController;
import com.kakaobank.profile.consumer.controller.ReadCustomerProfileController;
import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.factory.BeanFactory;
import com.kakaobank.profile.consumer.service.ReadAccountProfileService;
import com.kakaobank.profile.consumer.service.ReadCustomerProfileService;
import com.kakaobank.profile.consumer.service.WriteAccountLogService;
import com.kakaobank.profile.consumer.util.ConsumerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.get;

public class ProfileConsumer {
    private final static Logger logger = LoggerFactory.getLogger(ProfileConsumer.class);

    public static void main(String[] args) {
        if(args == null || args.length != 1) {
            logger.error("Usage : --configFile");
            System.exit(1);
        }

        AccountDao accountDao = BeanFactory.createAccountDao();
        CustomerDao customerDao = BeanFactory.createCustomerDao();

        startAccountLogConsumer(args[0], customerDao, accountDao);
        registerApiUris(customerDao, accountDao);
    }

    private static void startAccountLogConsumer(String configFilePath, CustomerDao customerDao, AccountDao accountDao) {
        Properties properties = ConsumerUtil.getProperties(configFilePath);
        if(properties == null) {
            logger.error("Failed to load config file. [Config File Path : " + configFilePath + "]");
            System.exit(1);
        }

        ReadStreamFromKafkaComponent readStreamFromKafkaComponent = getReadStreamFromKafkaComponent(customerDao, accountDao, properties);
        String topic = properties.getProperty("kafka.topic.name");
        int consumerTimeout = Integer.parseInt(properties.getProperty("kafka.consumer.timeout"));

        executeConsumer(readStreamFromKafkaComponent, topic, consumerTimeout);
    }

    private static void registerApiUris(CustomerDao customerDao, AccountDao accountDao) {
        ReadCustomerProfileController readCustomerProfileController = getReadCustomerProfileController(customerDao, accountDao);
        ReadAccountProfileController readAccountProfileController = getReadAccountProfileController(accountDao);

        get("/api/customer/:customerNumber", (req, res) -> {
            long customerNumber = Long.parseLong(req.params(":customerNumber"));

            return readCustomerProfileController.getCustomerProfile(customerNumber);
        });

        get("/api/customer/:customerNumber/account/:accountNumber", (req, res) -> {
            long customerNumber = Long.parseLong(req.params(":customerNumber"));
            String accountNumber = req.params(":accountNumber");
            String pageIndexParam = req.queryParams("pageIndex");
            String pageSizeParam = req.queryParams("pageSize");

            int pageIndex = Integer.parseInt(pageIndexParam == null ? "1" : pageIndexParam);
            int pageSize = Integer.parseInt(pageSizeParam == null ? "10" : pageSizeParam);

            return readAccountProfileController.getAccountProfile(customerNumber, accountNumber, pageIndex, pageSize);
        });
    }

    private static void executeConsumer(ReadStreamFromKafkaComponent readStreamFromKafkaComponent, String topic, int consumerTimeout) {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        Runnable task = () -> {
            readStreamFromKafkaComponent.readStream(topic, consumerTimeout);
        };

        logger.info("Start to profile consumer. [Topic : " + topic + "]");
        threadPool.execute(task);
    }

    private static ReadStreamFromKafkaComponent getReadStreamFromKafkaComponent(CustomerDao customerDao, AccountDao accountDao, Properties properties) {
        Properties kafkaConfigs = BeanFactory.createKafkaConfigs(properties);
        WriteAccountLogService writeAccountLogService = BeanFactory.createWriteAccountLogService(customerDao, accountDao);
        return BeanFactory.createReadStreamFromKafkaComponent(kafkaConfigs, writeAccountLogService);
    }

    private static ReadAccountProfileController getReadAccountProfileController(AccountDao accountDao) {
        ReadAccountProfileService service = BeanFactory.createReadAccountProfileService(accountDao);

        return BeanFactory.createReadAccountProfileController(service);
    }

    private static ReadCustomerProfileController getReadCustomerProfileController(CustomerDao customerDao, AccountDao accountDao) {
        ReadCustomerProfileService service = BeanFactory.createReadCustomerProfileService(customerDao, accountDao);

        return BeanFactory.createReadCustomerProfileController(service);
    }
}
