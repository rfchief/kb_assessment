package com.kakaobank.profile.consumer.dao;

import com.kakaobank.profile.consumer.dao.memory.MemoryAccountDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.log.AccountLog;
import com.kakaobank.profile.consumer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

public class AccountDaoTest {

    private AccountDao dao;

    @Before
    public void setup() {
        this.dao = new MemoryAccountDao();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test
    public void givenInvalidCustomerAndAccountNumber_whenFindById_thenReturnNullTest() {
        //given
        long invalidCustomerNumber = -1;
        String invalidAccountNumber = "";

        //when
        Account actual = dao.findByCustomerNumberAndAccountNumber(invalidCustomerNumber, invalidAccountNumber);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test
    public void givenAccount_whenInsert_thenSaveAccountToRepositoryTest() {
        //given
        Account givenAccount = TestDataFactory.getAccount();

        //when
        dao.insert(givenAccount);

        //then
        Account actual = dao.findByCustomerNumberAndAccountNumber(givenAccount.getCustomerNumber(), givenAccount.getAccountNumber());
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual, is(givenAccount));
    }

    @Test
    public void givenCustomerNumber_whenFindByCustomerNumber_thenReturnFirstAccountTest() {
        //given
        Account givenAccount = TestDataFactory.getAccount();
        dao.insert(givenAccount);

        //when
        Account actual = dao.findByCustomerNumber(givenAccount.getCustomerNumber());

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual, is(givenAccount));
    }

    @Test
    public void givenDepositAccountLog_whenInsertAccountLog_thenSaveDepositEventLogTest() {
        //given
        Account account = TestDataFactory.getAccount();
        AccountLog given = TestDataFactory.getDepositLog(1, account.getAccountNumber());
        dao.insert(account);

        //when
        dao.insert(given);

        //then
        List<AccountLog> actual = dao.findAllLogsByAccountNumber(given.getAccountNumber(), 0, 10);
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(1));
        Assert.assertThat(actual.get(0), is(given));
    }

    @Test
    public void givenAccountLogsAndInsertAccountLogs_whenFindByLargestAmount_thenReturnAccountLargestAmountTest() {
        //given
        Account givenAccount = TestDataFactory.getAccount();
        dao.insert(givenAccount);

        String accountNumber = givenAccount.getAccountNumber();
        List<AccountLog> logs = getTestAccountLogs(accountNumber);

        //when
        for (AccountLog log : logs)
            dao.insert(log);

        //then
        AccountAmount actual = dao.findAmountByAccountNumber(accountNumber);

        Assert.assertThat(dao.countAll(accountNumber), is(logs.size()));
        Assert.assertThat(actual.getBalance(), not(0));
        assertLargerThanZero(actual.getLargestDepositAmount());
        assertLargerThanZero(actual.getLargestWithdrawalAmount());
        assertLargerThanZero(actual.getLargestTransferAmount());
    }

    private void assertLargerThanZero(long actual) {
        Assert.assertTrue(actual >= 0 );
    }

    private List<AccountLog> getTestAccountLogs(String accountNumber) {
        List<AccountLog> logs = new ArrayList<>();
        logs.add(TestDataFactory.getDepositLog(1, accountNumber));
        logs.add(TestDataFactory.getTransferLog(2, accountNumber));
        logs.add(TestDataFactory.getTransferLog(3, accountNumber));
        logs.add(TestDataFactory.getDepositLog(4, accountNumber));
        logs.add(TestDataFactory.getWithdrawalLog(5, accountNumber));
        return logs;
    }
}
