package com.kakaobank.profile.producer.generator;

import com.kakaobank.profile.producer.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class CustomerProfileGeneratorTest {

    private CustomerProfileGenerator generator;

    @Before
    public void setup() {
        this.generator = new CustomerProfileGenerator();
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenMinusValueForCustomersAndMaxLengthOfName_whenDoGenerate_throwIllegalArgumentExceptionTest(){
        //given
        int size = -100;
        int maxLengthOfName = -1;

        //when
        List<Customer> actual = generator.doGenerator(size, maxLengthOfName);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenValidNumberOfCustomersAndInvalidMaxLengthOfName_whenDoGenerate_throwIllegalArgumentExceptionTest(){
        //given
        int size = 10;
        int maxLengthOfName = -1;

        //when
        List<Customer> actual = generator.doGenerator(size, maxLengthOfName);

        //then
        Assert.assertThat(actual, is(nullValue()));
    }

    @Test
    public void givenZeroValueForCustomers_whenDoGenerate_returnEmptyProfilesTest() {
        //given
        int size = 0;
        int maxLengthOfName = 10;

        //when
        List<Customer> actual = generator.doGenerator(size, maxLengthOfName);

        //then
        Assert.assertThat(actual.size(), is(0));
    }

    @Test
    public void givenNumberOfCustomers_whenDoGenerate_returnListOfCustomersTest() {
        //given
        int size = 100;
        int maxLengthOfName = 10;

        //when
        List<Customer> actual = generator.doGenerator(size, maxLengthOfName);

        //then
        Assert.assertThat(actual.size(), is(size));
        for (Customer customer : actual) {
            Assert.assertThat(customer.isValid(), is(true));
            System.out.println(customer);
        }
    }

}
