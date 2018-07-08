package com.kakaobank.profile.producer.util;

import com.kakaobank.profile.producer.model.Customer;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class TestDataFactory {

    public static Customer getCustomer() throws NoSuchAlgorithmException {
        LocalDateTime now = LocalDateTime.now();
        String name = StringUtil.randomString(10, true);
        String id = StringUtil.hash256(name + now.getNano());

        return new Customer(id, name, now);
    }

}
