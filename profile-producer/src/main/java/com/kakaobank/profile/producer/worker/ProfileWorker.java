package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;

import java.util.concurrent.Callable;

public class ProfileWorker implements Callable<Integer> {

    private final Customer customer;
    private final int maxLogCount;
    private final AccountLogGenerator accountLogGenerator;
    private final WriteDataToFileComponent writeDataToFileComponent;

    public ProfileWorker(Customer customer, int maxLogCount, AccountLogGenerator accountLogGenerator, WriteDataToFileComponent writeDataToFileComponent) {
        this.customer = customer;
        this.maxLogCount = maxLogCount;
        this.accountLogGenerator = accountLogGenerator;
        this.writeDataToFileComponent = writeDataToFileComponent;
    }

    @Override
    public Integer call() throws Exception {
        return doProcess();
    }

    private Integer doProcess() {
        return new Integer(0);
    }
}
