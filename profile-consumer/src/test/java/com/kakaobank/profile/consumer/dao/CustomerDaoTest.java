package com.kakaobank.profile.consumer.dao;

import com.kakaobank.profile.consumer.dao.memory.MemoryCustomerDao;
import com.kakaobank.profile.consumer.model.Customer;
import com.kakaobank.profile.consumer.util.ConsumerUtil;
import com.kakaobank.profile.consumer.util.StringUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;

public class CustomerDaoTest {

    private CustomerDao dao;

    @Before
    public void setup() {
        this.dao = new MemoryCustomerDao();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test
    public void givenEmptyCustomerNumber_whenFindByNumber_thenReturnNullTest() {
        //given
        long emptyCustomerNumber = -1;

        //when
        Customer actual = dao.findByNumber(emptyCustomerNumber);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test
    public void givenCustomer_whenInsert_thenInsertCustomerToRepositoryTest() {
        //given
        Customer givenCustomer = new Customer();
        givenCustomer.setNumber(ConsumerUtil.getRandomNumber(1000));
        givenCustomer.setName(StringUtil.randomString(7, true));
        givenCustomer.setJoinDt(LocalDateTime.now());

        //when
        dao.insert(givenCustomer);

        //then
        Customer actual = dao.findByNumber(givenCustomer.getNumber());
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual, is(givenCustomer));
    }

}
