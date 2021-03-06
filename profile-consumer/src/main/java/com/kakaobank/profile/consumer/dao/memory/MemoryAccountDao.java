package com.kakaobank.profile.consumer.dao.memory;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.log.AccountLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryAccountDao implements AccountDao {

    private final Map<Long, HashMap<String, Account>> accountRepository;
    private final Map<String, List<AccountLog>> accountLogRepository;
    private final Map<String, AtomicLong> accountLogSeqRepository;
    private final Map<String, AccountAmount> accountAmountRepository;

    public MemoryAccountDao() {
        this.accountRepository = new ConcurrentHashMap<>();
        this.accountLogRepository = new ConcurrentHashMap<>();
        this.accountLogSeqRepository = new ConcurrentHashMap<>();
        this.accountAmountRepository = new ConcurrentHashMap<>();

    }

    @Override
    public Account findByCustomerNumberAndAccountNumber(long customerNumber, String accountNumber) {
        if(isNotExistAccount(customerNumber))
            return null;

        Account account = accountRepository.get(customerNumber).get(accountNumber);
        if(account == null)
            return null;

        AccountAmount accountAmount = accountAmountRepository.get(accountNumber);
        account.setAmount(accountAmount);
        return account;
    }

    @Override
    public Account findByCustomerNumber(long customerNumber) {
        if(isNotExistAccount(customerNumber))
            return null;

        Account account = accountRepository.get(customerNumber).values().iterator().next();
        AccountAmount accountAmount = accountAmountRepository.get(account.getAccountNumber());
        account.setAmount(accountAmount);

        return account;
    }

    @Override
    public void insert(Account account) {
        HashMap<String, Account> accounts = null;
        if(isExistAccount(account.getCustomerNumber()))
            accounts = accountRepository.get(account.getCustomerNumber());
        else
            accounts = new HashMap<>();

        createAccount(account, accounts);
        insert(account.getAmount());
    }

    @Override
    public void insert(AccountAmount amount) {
        accountAmountRepository.put(amount.getAccountNumber(), amount);
    }

    @Override
    public void insert(AccountLog accountLog) {
        List<AccountLog> logs = null;
        if(isExistAccountLog(accountLog.getAccountNumber()))
            logs = accountLogRepository.get(accountLog.getAccountNumber());
        else {
            logs = new ArrayList<>();
            accountLogSeqRepository.put(accountLog.getAccountNumber(), new AtomicLong(0));
        }

        appendAccountLog(accountLog, logs);
        updateAccountAmount(accountLog);
    }

    @Override
    public List<AccountLog> findAllLogsByAccountNumber(String accountNumber, int offset, int size) {
        if(isNotExistAccountLog(accountNumber))
            return null;

        int totalCount = countAll(accountNumber);
        int fromIndex = offset * size;
        int toIndex = getToIndex(fromIndex, totalCount, size);

        return accountLogRepository.get(accountNumber).subList(fromIndex, toIndex);
    }

    @Override
    public int countAll(String accountNumber) {
        if(isNotExistAccountLog(accountNumber))
            return 0;

        return accountLogRepository.get(accountNumber).size();
    }

    @Override
    public AccountAmount findAmountByAccountNumber(String accountNumber) {
        if(!accountAmountRepository.containsKey(accountNumber))
            return null;

        return accountAmountRepository.get(accountNumber);
    }

    private boolean isExistAccount(long customerNumber) {
        return accountRepository.containsKey(customerNumber);
    }

    private boolean isNotExistAccount(long customerNumber) {
        return !isExistAccount(customerNumber);
    }

    private void createAccount(Account account, HashMap<String, Account> accounts) {
        accounts.put(account.getAccountNumber(), account);
        accountRepository.put(account.getCustomerNumber(), accounts);
    }

    private boolean isExistAccountLog(String accountNumber) {
        return accountLogRepository.containsKey(accountNumber);
    }

    private boolean isNotExistAccountLog(String accountNumber) {
        return !isExistAccountLog(accountNumber);
    }

    private void appendAccountLog(AccountLog accountLog, List<AccountLog> logs) {
        long seq = accountLogSeqRepository.get(accountLog.getAccountNumber()).getAndIncrement();
        accountLog.setSeq(seq);

        logs.add(accountLog);
        accountLogRepository.put(accountLog.getAccountNumber(), logs);
    }

    private void updateAccountAmount(AccountLog accountLog) {
        AccountAmount amount = findAmountByAccountNumber(accountLog.getAccountNumber());
        amount.update(accountLog);

        accountAmountRepository.put(accountLog.getAccountNumber(), amount);
    }

    private int getToIndex(int fromIndex, int totalCount, int size) {
        return fromIndex + size > totalCount ? totalCount : fromIndex + size;
    }
}
