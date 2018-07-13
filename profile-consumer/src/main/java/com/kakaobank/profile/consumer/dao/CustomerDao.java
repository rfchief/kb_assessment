package com.kakaobank.profile.consumer.dao;

import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.Customer;

public interface CustomerDao {
    Customer findByNumber(long number);

    int insert(Customer customer);

    Account findByCustomerNumberAndAccountNumber(long customerNumber, String accountNumber);

    void insert(Account account);
}
