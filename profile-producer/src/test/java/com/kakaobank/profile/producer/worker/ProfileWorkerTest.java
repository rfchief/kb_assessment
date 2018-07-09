package com.kakaobank.profile.producer.worker;

import com.kakaobank.profile.producer.generator.AccountLogGenerator;
import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.service.WriteProfileService;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.is;

public class ProfileWorkerTest {

    private ProfileWorker worker;
    private Customer customer;
    private int maxLogCount;

    @Before
    public void setup() throws NoSuchAlgorithmException, IOException {
        customer = TestDataFactory.getCustomer();
        maxLogCount = 100;
        AccountLogGenerator accountLogGenerator = TestDataFactory.getAccountLogGenerator();
        WriteProfileService writeProfileService = TestDataFactory.getWriteProfileService();
        this.worker = new ProfileWorker(customer, maxLogCount, accountLogGenerator, writeProfileService);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test
    public void givenEmptyCustomerAndMaxLogCount_whenCall_thenReturnZeroTest() throws Exception {
        //given
        ProfileWorker givenProfileWorker = new ProfileWorker(null, maxLogCount, null, null);

        //when
        int actual = givenProfileWorker.call();

        //then
        Assert.assertThat(actual, is(0));
    }

}
