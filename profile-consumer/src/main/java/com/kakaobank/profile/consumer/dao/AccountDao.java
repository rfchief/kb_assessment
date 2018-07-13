package com.kakaobank.profile.consumer.dao;

import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.log.AccountLog;

import java.util.List;

public interface AccountDao {
    Account findByCustomerNumberAndAccountNumber(long customerNumber, String accountNumber);

    void insert(Account account);

    void insert(AccountAmount amount);

    void insert(AccountLog accountLog);

    List<AccountLog> findAllLogsByAccountNumber(String accountNumber, int offset, int size);

    int countAll(String accountNumber);

    AccountAmount findAmountByAccountNumber(String accountNumber);
}
