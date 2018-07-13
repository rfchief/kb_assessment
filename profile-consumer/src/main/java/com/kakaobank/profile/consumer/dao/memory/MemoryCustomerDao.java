package com.kakaobank.profile.consumer.dao.memory;

import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCustomerDao implements CustomerDao {

    private Map<Long, Customer> customerRepository = new ConcurrentHashMap<>();
    private Map<Long, HashMap<String, Account>> accountRepository = new ConcurrentHashMap<>();

    @Override
    public Customer findByNumber(long number) {
        return customerRepository.get(number);
    }

    @Override
    public int insert(Customer customer) {
        customerRepository.put(customer.getNumber(), customer);
        return 1;
    }

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
}