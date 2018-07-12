package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.EventType;
import com.kakaobank.profile.producer.service.WriteProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileWorker {
    private final Logger logger = LoggerFactory.getLogger(ProfileWorker.class);

    private final Customer customer;
    private final int maxLogCount;
    private final AccountLogGenerator accountLogGenerator;
    private final WriteProfileService writeProfileService;

    public ProfileWorker(Customer customer, int maxLogCount,
                         AccountLogGenerator accountLogGenerator,
                         WriteProfileService writeProfileService) {
        this.customer = customer;
        this.maxLogCount = maxLogCount;
        this.accountLogGenerator = accountLogGenerator;
        this.writeProfileService = writeProfileService;
    }

    public Integer execute() {
        try {
            writeProfileService.open();
            Integer logCount = doProcess();
            writeProfileService.close();

            return logCount;
        } catch (IOException e) {
            logger.error("Failed to sending customer profiles. [ Message : " + e.getMessage() + "]");
        }

        return 0;

    }

    private Integer doProcess() {
        int logCount = sendJoinAndCreateAccountEventLogs(getJoinAndCreateAccountEventLog(), 0);
        return generateAndSendEventLogs(logCount);
    }

    private int generateAndSendEventLogs(int logCount) {
        int initialSize = logCount;
        for (int i = initialSize; i < maxLogCount + initialSize; i++) {
            EventLog eventLog = accountLogGenerator.doGenerate(customer);
            boolean isSuccess = writeProfileService.write(eventLog);

            if(isSuccess)
                logCount++;
            else
                logger.error(String.format("Failed to write eventLog. [Customer ID : %s]", customer.getNumber()));
        }
        return logCount;
    }

    private List<EventLog> getJoinAndCreateAccountEventLog() {
        List<EventLog> logs = new ArrayList<>();
        EventLog joinEventLog = accountLogGenerator.doGenerate(customer, EventType.JOIN);
        logs.add(joinEventLog);

        EventLog createAccountEventLog = accountLogGenerator.doGenerate(customer, EventType.CREATE);
        logs.add(createAccountEventLog);

        return logs;
    }

    private int sendJoinAndCreateAccountEventLogs(List<EventLog> logs, int logCount) {
        List<Boolean> results = writeProfileService.write(logs);
        for (Boolean result : results)
            if(result) logCount++;

        return logCount;
    }
}
