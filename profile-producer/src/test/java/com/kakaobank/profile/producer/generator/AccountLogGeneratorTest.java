package com.kakaobank.profile.producer.generator;

import com.kakaobank.profile.producer.model.*;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.*;

public class AccountLogGeneratorTest {

    private AccountLogGenerator generator;

    @Before
    public void setup() {
        int maxBoundForAmount = 1000;
        this.generator = new AccountLogGenerator(maxBoundForAmount);
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyCustomerAndEventType_whenDoGenerate_thenThrowIllegalArgumentExceptionTest() {
        //given
        Customer emptyCustomer = null;
        EventType eventType = EventType.JOIN;

        //when
        EventLog actual = generator.doGenerate(emptyCustomer, eventType);

        //then
        Assert.assertThat(actual, is(notNullValue()));
    }

    @Test
    public void givenCustomerAndJoinEventType_whenDoGenerate_thenReturnJoinEventLogTest() throws NoSuchAlgorithmException {
        //given
        Customer givenCustomer = TestDataFactory.getCustomer();
        EventType givenEventType = EventType.JOIN;

        //when
        EventLog actual = generator.doGenerate(givenCustomer, givenEventType);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual, is(instanceOf(JoinLog.class)));
    }

    @Test
    public void givenCustomerAndCreateEventType_whenDoGenerate_thenReturnCreateEventLogTest() throws NoSuchAlgorithmException {
        //given
        Customer givenCustomer = TestDataFactory.getCustomer();
        EventType givenEventType = EventType.CREATE;

        //when
        EventLog actual = generator.doGenerate(givenCustomer, givenEventType);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual, is(instanceOf(CreateAccountLog.class)));
    }

}
