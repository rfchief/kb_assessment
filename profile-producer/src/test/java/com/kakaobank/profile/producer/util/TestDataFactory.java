package com.kakaobank.profile.producer.util;

import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.generator.CustomerProfileGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.service.WriteProfileService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class TestDataFactory {

    public static Customer getCustomer() throws NoSuchAlgorithmException {
        LocalDateTime now = LocalDateTime.now();
        String name = StringUtil.randomString(10, true);
        String id = StringUtil.hash256(name + now.getNano());

        return new Customer(id, name, now);
    }

    public static CustomerProfileGenerator getCustomerProfileGenerator() {
        return new CustomerProfileGenerator();
    }

    public static AccountLogGenerator getAccountLogGenerator() {
        return getAccountLogGenerator(1000000);
    }

    public static AccountLogGenerator getAccountLogGenerator(int maxBoundForAmount) {
        return new AccountLogGenerator(maxBoundForAmount);
    }

    public static WriteDataToFileComponent getWriteDataToFileComponent() throws IOException, NoSuchAlgorithmException {
        Customer customer = getCustomer();
        return new WriteDataToFileComponent(customer.getId() + ".txt");
    }

    public static WriteProfileService getWriteProfileService() throws IOException, NoSuchAlgorithmException {
        return new WriteProfileService(getWriteDataToFileComponent());
    }

    public static EventLog getEventLog() throws NoSuchAlgorithmException {
        return getAccountLogGenerator().doGenerate(getCustomer());
    }
}
