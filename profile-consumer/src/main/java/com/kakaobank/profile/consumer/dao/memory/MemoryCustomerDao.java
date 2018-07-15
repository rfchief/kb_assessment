package com.kakaobank.profile.consumer.dao.memory;

import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.model.Customer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCustomerDao implements CustomerDao {

    private final Map<Long, Customer> customerRepository;

    public MemoryCustomerDao() {
        this.customerRepository = new ConcurrentHashMap<>();
    }

    @Override
    public Customer findByNumber(long number) {
        return customerRepository.get(number);
    }

    @Override
    public int insert(Customer customer) {
        customerRepository.put(customer.getNumber(), customer);
        return 1;
    }
}
