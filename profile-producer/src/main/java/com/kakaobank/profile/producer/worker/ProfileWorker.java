package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.EventType;
import com.kakaobank.profile.producer.service.WriteProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ProfileWorker implements Callable<Integer> {
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

    @Override
    public Integer call() throws Exception {
        writeProfileService.open();
        Integer logCount = doProcess();
        writeProfileService.close();

        return logCount;

    }

    private Integer doProcess() {
        int logCount = sendJoinAndCreateAccountEventLogs(getJoinAndCreateAccountEventLog(), 0);
        return generateAndSendEventLogs(logCount);
    }

    private int generateAndSendEventLogs(int logCount) {
        int initialSize = logCount;
        for (int i = initialSize; i < maxLogCount + initialSize; i++) {
            EventLog eventLog = getEventLog(i);
            boolean isSuccess = writeProfileService.write(eventLog);

            if(isSuccess)
                logCount++;
            else
                logger.error(String.format("Failed to write eventLog. [Customer ID : %s Event Log Seq : %ld ]", customer.getId(), eventLog.getSeq()));
        }
        return logCount;
    }

    private EventLog getEventLog(int i) {
        EventLog eventLog = accountLogGenerator.doGenerate(customer);
        eventLog.setSeq(i);
        return eventLog;
    }

    private List<EventLog> getJoinAndCreateAccountEventLog() {
        List<EventLog> logs = new ArrayList<>();
        EventLog joinEventLog = accountLogGenerator.doGenerateJoinEvent(customer);
        joinEventLog.setSeq(0l);
        logs.add(joinEventLog);

        EventLog createAccountEventLog = accountLogGenerator.doGenerate(customer, EventType.CREATE);
        createAccountEventLog.setSeq(1l);
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
