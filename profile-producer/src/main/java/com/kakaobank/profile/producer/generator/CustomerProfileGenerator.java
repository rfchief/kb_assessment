package com.kakaobank.profile.producer.generator;

import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.util.StringUtil;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerProfileGenerator {

    public List<Customer> doGenerator(int size, int maxLengthOfName) {
        if(size < 0 || maxLengthOfName <= 0)
            throw new IllegalArgumentException("Customer size or max length of name is not valid!!!");

        List<Customer> customers = new ArrayList<Customer>();
        for (int i = 0; i < size; i++) {
            try{
                LocalDateTime joinDt = LocalDateTime.now();
                String name = generateName(maxLengthOfName);
                String id = StringUtil.hash256(name + joinDt.getNano());

                customers.add(new Customer(id, name, joinDt));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return customers;
    }

    private String generateName(int maxLengthOfName){
        return StringUtil.randomString(maxLengthOfName, true);
    }

}
