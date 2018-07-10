package com.kakaobank.profile.producer.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.producer.component.impl.MessageConverter;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class MessageConverterTest {

    private MessageConverter messageConverter;

    @Before
    public void setup() {
        ObjectMapper objectMapper = getObjectMapper();

        this.messageConverter = new MessageConverter(objectMapper);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEmptyEventLog_whenConvert_thenThrowIllegalArgumentTest() throws JsonProcessingException {
        //given
        EventLog emptyEventLog = null;

        //when
        String actual = messageConverter.convert(emptyEventLog);

        //then
        Assert.assertThat(actual, is(notNullValue()));
    }

    @Test
    public void givenEventLog_whenConvert_thenReturnSuccessTest() throws IOException, NoSuchAlgorithmException {
        //given
        EventLog givenEventLog = TestDataFactory.getEventLog();

        //when
        String actual = messageConverter.convert(givenEventLog);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        EventLog actualEventLog = messageConverter.read(actual);
        assertGivenAndActual(givenEventLog, actualEventLog);
    }

    private void assertGivenAndActual(EventLog eventLog, EventLog actualEventLog) {
        Assert.assertThat(actualEventLog.getSeq(), is(eventLog.getSeq()));
        Assert.assertThat(actualEventLog.getAmount(), is(eventLog.getAmount()));
        Assert.assertThat(actualEventLog.getEventType(), is(eventLog.getEventType()));
        Assert.assertThat(actualEventLog.getEventTime(), is(eventLog.getEventTime()));
        Assert.assertThat(actualEventLog.getCustomer().getId(), is(eventLog.getCustomer().getId()));
        Assert.assertThat(actualEventLog.getCustomer().getName(), is(eventLog.getCustomer().getName()));
        Assert.assertThat(actualEventLog.getCustomer().getJoinDt(), is(eventLog.getCustomer().getJoinDt()));
    }

}
