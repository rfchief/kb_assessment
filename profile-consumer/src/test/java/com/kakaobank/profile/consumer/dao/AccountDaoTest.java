package com.kakaobank.profile.consumer.dao;

import com.kakaobank.profile.consumer.dao.mock.MockAccountDao;
import com.kakaobank.profile.consumer.dao.mock.MockCustomerDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.Customer;
import com.kakaobank.profile.consumer.model.log.AccountLog;
import com.kakaobank.profile.consumer.util.ConsumerUtil;
import com.kakaobank.profile.consumer.util.StringUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

public class AccountDaoTest {

    private AccountDao dao;

    @Before
    public void setup() {
        this.dao = new MockAccountDao();
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
        Account givenAccount = new Account();
        givenAccount.setCustomerNumber(ConsumerUtil.getRandomNumber(1000));
        givenAccount.setAccountNumber(String.valueOf(ConsumerUtil.getRandomNumber(100000000)));
        givenAccount.setBalance(ConsumerUtil.getRandomNumber(10000000));

        //when
        dao.insert(givenAccount);

        //then
        Account actual = dao.findByCustomerNumberAndAccountNumber(givenAccount.getCustomerNumber(), givenAccount.getAccountNumber());
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual, is(givenAccount));
    }

    @Test
    public void givenDepositAccountLog_whenInsertAccountLog_thenSaveDepositEventLogTest() {
        //given
        AccountLog given = new AccountLog();

        //when
        dao.insert(given);

        //then
        List<AccountLog> actual = dao.findAccountLogByAccountNumber(given.getAccountNumber());
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.size(), is(1));
        Assert.assertThat(actual.get(0), is(given));
    }
}
