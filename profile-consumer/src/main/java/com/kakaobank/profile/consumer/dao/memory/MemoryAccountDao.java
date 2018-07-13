package com.kakaobank.profile.consumer.dao.memory;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.log.AccountLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryAccountDao implements AccountDao {

    private Map<Long, HashMap<String, Account>> accountRepository = new HashMap<>();
    private Map<String, List<AccountLog>> accountLogRepository = new HashMap<>();

    @Override
    public Account findByCustomerNumberAndAccountNumber(long customerNumber, String accountNumber) {
        if(!accountRepository.containsKey(customerNumber))
            return null;

        return accountRepository.get(customerNumber).get(accountNumber);
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

        logs.add(accountLog);
        accountLogRepository.put(accountLog.getAccountNumber(), logs);
    }

    @Override
    public List<AccountLog> findAllLogsByAccountNumber(String accountNumber, int offset, int size) {
        if(!accountLogRepository.containsKey(accountNumber))
            return null;

        int totalCount = accountLogRepository.get(accountNumber).size();
        int fromIndex = offset * size;
        int toIndex = fromIndex + size > totalCount ? totalCount : fromIndex + size;

        return accountLogRepository.get(accountNumber).subList(fromIndex, toIndex);
    }

}
