package com.kakaobank.profile.producer.generator;

import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.EventType;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

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
    public void givenCustomer_whenDoGenerateJoinEvent_thenReturnJoinEventLogTest() throws NoSuchAlgorithmException {
        //given
        Customer givenCustomer = TestDataFactory.getCustomer();

        //when
        EventLog actual = generator.doGenerateJoinEvent(givenCustomer);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getEventType(), is(EventType.JOIN));
        Assert.assertThat(actual.getAmount(), is(0));
        Assert.assertThat(actual.getCustomer(), is(givenCustomer));
    }

    @Test
    public void givenCustomerAndCreateEvent_whenDoGenerate_thenReturnCreateEventLogTest() throws NoSuchAlgorithmException {
        //given
        Customer givenCustomer = TestDataFactory.getCustomer();
        EventType createAccountEventType = EventType.CREATE;

        //when
        EventLog actual = generator.doGenerate(givenCustomer, createAccountEventType);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getEventType(), is(EventType.CREATE));
        Assert.assertTrue(actual.getAmount() >= 0);
        Assert.assertThat(actual.getCustomer(), is(givenCustomer));
    }

    @Test
    public void givenCustomer_whenDoGenerate_thenReturnRandomEventLogExceptCreateAndJoinEventTest() throws NoSuchAlgorithmException {
        //given
        Customer givenCustomer = TestDataFactory.getCustomer();

        //when
        EventLog actual = generator.doGenerate(givenCustomer);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        Assert.assertThat(actual.getEventType(), not(EventType.JOIN));
        Assert.assertThat(actual.getEventType(), not(EventType.CREATE));
        Assert.assertTrue(actual.getAmount() >= 0);
        Assert.assertThat(actual.getCustomer(), is(givenCustomer));
    }
}
