package com.kakaobank.profile.producer.service.impl;

import com.kakaobank.profile.producer.Factory.BeanFactory;
import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.service.ExecuteProcessService;
import com.kakaobank.profile.producer.worker.ProfileWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteProcessServiceImpl implements ExecuteProcessService {
    private final Logger logger = LoggerFactory.getLogger(ExecuteProcessServiceImpl.class);
    private final CustomerProfileGenerator customerProfileGenerator;

    public ExecuteProcessServiceImpl(CustomerProfileGenerator customerProfileGenerator) {
        this.customerProfileGenerator = customerProfileGenerator;
    }

    public void doProcess(Properties properties, int maxThreadCount, String logFilePath) {
        if(properties == null || logFilePath == null || logFilePath == "")
            throw new IllegalArgumentException("Property or logFilePath is Empty!!");

        int maxLogCount = Integer.parseInt(properties.getProperty("log.max.count", "100"));
        String kafkaTopic = properties.getProperty("kafka.topic.name");
        Properties kafkaConfigs = getKafkaConfigs(properties);

        List<Customer> customers = getCustomers(properties);
        ExecutorService executorService = createThreadPool(maxThreadCount);
        CountDownLatch countDownLatch = new CountDownLatch(customers.size());

        try {
            for (Customer customer : customers) {
                String filePathAndName = getFilePathAndName(logFilePath, customer.getId());
                ProfileWorker profileWorker = getProfileWorker(customer, maxLogCount, filePathAndName, kafkaConfigs, kafkaTopic);

                Runnable task = getRunnableThread(countDownLatch, profileWorker);

                executorService.execute(task);
            }

            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        executorService.shutdown();
    }

    private List<Customer> getCustomers(Properties properties) {
        int numCustomers = Integer.parseInt(properties.getProperty("customer.count", "10"));
        int maxLengthOfName = Integer.parseInt(properties.getProperty("customer.name.max.length", "20"));
        return customerProfileGenerator.doGenerator(numCustomers, maxLengthOfName);
    }

    private Runnable getRunnableThread(CountDownLatch countDownLatch, ProfileWorker profileWorker) {
        return () -> {
                        profileWorker.execute();
                        countDownLatch.countDown();
                    } ;
    }

    private ProfileWorker getProfileWorker(Customer customer, int maxLogCount, String logFileName, Properties kafkaConfigs, String kafkaTopic) {
        return BeanFactory.createProfileWorker(customer, maxLogCount, logFileName, kafkaTopic, kafkaConfigs);
    }

    private ExecutorService createThreadPool(int maxThreadCount) {
        logger.info("Created Fixed Thread Pool [Size : " + maxThreadCount + "]");
        return Executors.newFixedThreadPool(maxThreadCount);
    }

    private String getFilePathAndName(String logFilePath, String customerId) {
        if(logFilePath.endsWith("/"))
            return logFilePath + customerId;

        return logFilePath + "/" + customerId;
    }

    private Properties getKafkaConfigs(Properties properties) {
        return BeanFactory.createKafkaConfigs(properties);
    }

}
