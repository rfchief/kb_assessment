package com.kakaobank.profile.consumer.service;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryAccountDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryCustomerDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.Customer;
import com.kakaobank.profile.consumer.model.dto.CustomerProfileDTO;
import com.kakaobank.profile.consumer.service.impl.ReadCustomerCustomerProfileServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class ReadCustomerProfileServiceTest {

    private ReadCustomerProfileService service;
    private CustomerDao customerDao;
    private AccountDao accountDao;
    private Customer givenCustomer;

    @Before
    public void setup() {
        this.customerDao = new MemoryCustomerDao();
        this.accountDao = new MemoryAccountDao();

        initCustomer();
        initAccount();

        this.service = new ReadCustomerCustomerProfileServiceImpl(customerDao, accountDao);
    }

    private void initAccount() {
        Account givenAccount = new Account();
        givenAccount.setCustomerNumber(givenCustomer.getNumber());
        givenAccount.setAccountNumber("1234567890");
        givenAccount.setCreateDt(LocalDateTime.now());

        AccountAmount amount = new AccountAmount();
        amount.setAccountNumber(givenAccount.getAccountNumber());
        amount.setLargestDepositAmount(10000);
        amount.setLargestWithdrawalAmount(100);
        amount.setLargestTransferAmount(2000);
        amount.setBalance(10000 - 100 - 2000);

        givenAccount.setAmount(amount);

        accountDao.insert(givenAccount);
    }

    private void initCustomer() {
        givenCustomer = new Customer();
        givenCustomer.setName("James");
        givenCustomer.setNumber(11111);
        givenCustomer.setJoinDt(LocalDateTime.now());
        customerDao.insert(givenCustomer);
    }


    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!");
    }

    @Test
    public void givenNotExistCustomerNumber_whenGetCustomerProfile_theReturnNullTest() {
        //given
        long notExistCustomerNumber = -1;

        //when
        CustomerProfileDTO actual = service.getCustomerProfile(notExistCustomerNumber);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test
    public void givenCustomerNumber_whenGetCustomerProfile_thenReturnCustomerProfileDTOTest() {
        //given
        long givenCustomerNumber = givenCustomer.getNumber();

        //when
        CustomerProfileDTO actual = service.getCustomerProfile(givenCustomerNumber);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getCustomerNumber(), is(givenCustomer.getNumber()));
        Assert.assertThat(actual.getCustomerName(), is(givenCustomer.getName()));
        Assert.assertThat(actual.getJoinDt(), is(givenCustomer.getJoinDt()));
        Assert.assertTrue(actual.getLargestTransferAmount() >= 0);
        Assert.assertTrue(actual.getLargestDepositAmount() >= 0);
        Assert.assertTrue(actual.getLargestWithdrawalAmount() >= 0);
    }
}
