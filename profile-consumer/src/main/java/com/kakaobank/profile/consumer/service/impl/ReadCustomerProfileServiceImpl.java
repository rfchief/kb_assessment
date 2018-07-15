package com.kakaobank.profile.consumer.service.impl;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.Customer;
import com.kakaobank.profile.consumer.model.dto.CustomerProfileDTO;
import com.kakaobank.profile.consumer.service.ReadCustomerProfileService;

public class ReadCustomerProfileServiceImpl implements ReadCustomerProfileService {
    private final CustomerDao customerDao;
    private final AccountDao accountDao;

    public ReadCustomerProfileServiceImpl(CustomerDao customerDao, AccountDao accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    @Override
    public CustomerProfileDTO getCustomerProfileBy(long customerNumber) {
        Customer customer = customerDao.findByNumber(customerNumber);
        if(customer == null)
            return null;

        Account account = accountDao.findByCustomerNumber(customer.getNumber());

        return createCustomerProfileDTO(customer, account);
    }

    private CustomerProfileDTO createCustomerProfileDTO(Customer customer, Account account) {
        CustomerProfileDTO customerProfileDto = new CustomerProfileDTO();
        customerProfileDto.setCustomerNumber(customer.getNumber());
        customerProfileDto.setCustomerName(customer.getName());
        customerProfileDto.setJoinDt(customer.getJoinDt());
        customerProfileDto.setLargestDepositAmount(account.getLargestDepositAmount());
        customerProfileDto.setLargestWithdrawalAmount(account.getLargestWithdrawalAmount());
        customerProfileDto.setLargestTransferAmount(account.getLargestTransferAmount());

        return customerProfileDto;
    }
}
