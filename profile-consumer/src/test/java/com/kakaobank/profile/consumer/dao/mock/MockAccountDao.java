package com.kakaobank.profile.consumer.dao.mock;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.log.AccountLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockAccountDao implements AccountDao {

    private Map<Long, HashMap<String, Account>> accountRepository = new HashMap<>();
    private Map<Long, List<AccountLog>> accountLogRepository = new HashMap<>();

    @Override
    public Account findByCustomerNumberAndAccountNumber(long customerNumber, String accountNumber) {
        return null;
    }

    @Override
    public void insert(Account account) {
        HashMap<String, Account> accounts = null;
        if(!accountRepository.containsKey(account.getCustomerNumber()))
            accounts = new HashMap<>();

        accounts.put(account.getAccountNumber(), account);
        accountRepository.put(account.getCustomerNumber(), accounts);
    }

    @Override
    public void insert(AccountLog accountLog) {
        List<AccountLog> logs = null;
        if(!accountRepository.containsKey(accountLog.getAccountNumber()))
            logs = new ArrayList<>();
    }

    @Override
    public List<AccountLog> findAccountLogByAccountNumber(String accountNumber) {
        return null;
    }
}
