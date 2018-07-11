package com.kakaobank.profile.producer;

import com.kakaobank.profile.producer.Factory.BeanFactory;
import com.kakaobank.profile.producer.service.ExecuteProcessService;
import com.kakaobank.profile.producer.util.ProducerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Properties;

public class CustomerProfileProducer {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProfileProducer.class);

    public static void main(String[] args) {
        if(args == null || args.length == 0) {
            logger.error("Usage : --configFile --maxThreadCount --logFilePath");
            System.exit(1);
        }

        doProcess(args);

        System.exit(0);
    }

    private static void doProcess(String[] args) {
        String configFile = args[0];
        String logFilePath = args[2];
        int maxThreadCount = Integer.parseInt(args[1]);

        ExecuteProcessService executeProcessService = BeanFactory.createExecuteProcessService();
        Properties properties = ProducerUtil.getProperties(configFile);
        if(properties == null) {
            logger.error("Failed to load config file. [Config File Path : " + configFile + "]");
            System.exit(1);
        }

        logger.info("Start to execute process of customer profile producer.");
        Long startTime = Instant.now().toEpochMilli();

        executeProcessService.doProcess(properties, maxThreadCount, logFilePath);

        Long endTime = Instant.now().toEpochMilli();
        Long elapsedTime = endTime - startTime;
        logger.info("Success send all profiles. [Execution time : " + elapsedTime + " ms]");
    }
}
