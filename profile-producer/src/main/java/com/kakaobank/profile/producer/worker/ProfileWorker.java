package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.EventType;
import com.kakaobank.profile.producer.service.WriteProfileService;

import java.util.concurrent.Callable;

public class ProfileWorker implements Callable<Integer> {

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
        return doProcess();
    }

    private Integer doProcess() {
        int logCount = 0;
        EventLog joinEventLog = accountLogGenerator.doGenerateJoinEvent(customer);
        EventLog createAccountEventLog = accountLogGenerator.doGenerate(customer, EventType.CREATE);

        //send join and create eventlog
        writeProfileService.write(joinEventLog);
        writeProfileService.write(createAccountEventLog);

        for (int i = 0; i < maxLogCount; i++) {
            EventLog eventLog = accountLogGenerator.doGenerate(customer);
            boolean isSuccess = writeProfileService.write(eventLog);

            if(isSuccess)
                logCount++;
            else
                System.out.println("Failed to write eventLog. [Event Log ID : " + eventLog.getSeq() + "]");
        }

        return logCount;
    }
}
