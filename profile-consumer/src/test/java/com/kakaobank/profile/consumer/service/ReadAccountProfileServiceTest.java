package com.kakaobank.profile.consumer.service;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.memory.MemoryAccountDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.EventType;
import com.kakaobank.profile.consumer.model.dto.AccountProfileDTO;
import com.kakaobank.profile.consumer.model.log.AccountLog;
import com.kakaobank.profile.consumer.service.impl.ReadAccountProfileServiceImpl;
import com.kakaobank.profile.consumer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;

public class ReadAccountProfileServiceTest {

    private AccountDao accountDao;
    private ReadAccountProfileService service;
    private Account account;

    @Before
    public void setup() {
        this.accountDao = new MemoryAccountDao();

        initAccount();
        accountDao.insert(this.account);

        List<AccountLog> logs = getAccountLogs();
        for (AccountLog log : logs)
            accountDao.insert(log);

        this.service = new ReadAccountProfileServiceImpl(accountDao);
    }

    private List<AccountLog> getAccountLogs() {
        List<AccountLog> logs = new ArrayList<>();
        AccountLog accountLog = new AccountLog();
        accountLog.setSeq(1);
        accountLog.setAccountNumber(account.getAccountNumber());
        accountLog.setEventType(EventType.DEPOSIT);
        accountLog.setAmount(10000);
        accountLog.setDateTime(LocalDateTime.now());
        logs.add(accountLog);

        accountLog = new AccountLog();
        accountLog.setSeq(2);
        accountLog.setAccountNumber(account.getAccountNumber());
        accountLog.setEventType(EventType.WITHDRAWAL);
        accountLog.setAmount(100);
        accountLog.setDateTime(LocalDateTime.now());
        logs.add(accountLog);

        accountLog = new AccountLog();
        accountLog.setSeq(3);
        accountLog.setAccountNumber(account.getAccountNumber());
        accountLog.setEventType(EventType.TRANSFER);
        accountLog.setAmount(2000);
        accountLog.setDateTime(LocalDateTime.now());
        logs.add(accountLog);

        accountLog = new AccountLog();
        accountLog.setSeq(4);
        accountLog.setAccountNumber(account.getAccountNumber());
        accountLog.setEventType(EventType.WITHDRAWAL);
        accountLog.setAmount(400);
        accountLog.setDateTime(LocalDateTime.now());
        logs.add(accountLog);

        return logs;
    }


    private void initAccount() {
        this.account = TestDataFactory.getAccount();

        AccountAmount amount = new AccountAmount();
        amount.setAccountNumber(account.getAccountNumber());
        amount.setBalance(1000);
        amount.setLargestTransferAmount(2000);
        amount.setLargestWithdrawalAmount(1000);
        amount.setLargestDepositAmount(10000);
        this.account.setAmount(amount);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenNotExistCustomerNumberAndInvalidAccountNumber_whenGetAccountProfile_thenReturnNullTest() {
        //given
        long notExistCustomerNumber = -1;
        String invalidAccountNumber = null;

        //when
        AccountProfileDTO actual = service.getAccountProfileBy(notExistCustomerNumber, invalidAccountNumber,1, 10);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test
    public void givenExistCustomerNumberAndInvalidAccountNumber_whenGetAccountProfile_thenReturnNullTest() {
        //given
        long customerNumber = 1111;
        String invalidAccountNumber = null;

        //when
        AccountProfileDTO actual = service.getAccountProfileBy(customerNumber, invalidAccountNumber, 1, 10);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test
    public void givenExistCustomerNumberAndExistAccountNumber_whenGetAccountProfile_thenReturnAccountProfileDTOTest() {
        //given
        int pageIndex = 1;
        int pageSize = 10;

        //when
        AccountProfileDTO actual = service.getAccountProfileBy(account.getCustomerNumber(), account.getAccountNumber(), pageIndex, pageSize);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getCustomerNumber(), is(account.getCustomerNumber()));
        Assert.assertThat(actual.getAccountNumber(), is(account.getAccountNumber()));
        Assert.assertThat(actual.getCreateDt(), is(account.getCreateDt()));
        Assert.assertTrue(actual.getBalance() >= 0);
        Assert.assertThat(actual.getDepositLogs().size(), is(1));
        Assert.assertThat(actual.getWithdrawalLogs().size(), is(2));
        Assert.assertThat(actual.getTransferLogs().size(), is(1));
    }
}
