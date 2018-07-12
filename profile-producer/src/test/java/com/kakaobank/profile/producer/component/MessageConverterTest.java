package com.kakaobank.profile.producer.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kakaobank.profile.producer.component.impl.MessageConverter;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.TransferLog;
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
        EventLog givenEventLog = TestDataFactory.getTransferEventLog();

        //when
        String actual = messageConverter.convert(givenEventLog);

        System.out.println(actual);

        //then
        Assert.assertThat(actual, is(notNullValue()));
        EventLog actualEventLog = messageConverter.read(actual, TransferLog.class);
        Assert.assertThat(givenEventLog.toString(), is(actualEventLog.toString()));
    }
}
