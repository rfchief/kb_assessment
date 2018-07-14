package com.kakaobank.profile.consumer.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobank.profile.consumer.model.EventType;
import com.kakaobank.profile.consumer.model.dto.log.AccountLogDTO;
import com.kakaobank.profile.consumer.util.TestDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class MessageConverterTest {

    private MessageConverter converter;

    @Before
    public void setup() {
        this.converter = new MessageConverter(new ObjectMapper());
    }

    @Test
    public void doNothingTest() {
        System.out.println("Everything is OK!!");
    }

    @Test
    public void givenJsonString_whenReadValue_thenReturnAccountLogDTOTest() throws IOException {
        //given
        String logPath = System.getProperty("user.dir") + "/src/test/resources/data/json_account_logs.log";
        List<String> jsonStrings = TestDataFactory.getJsonAccountLogs(logPath);
        List<AccountLogDTO> logs = new ArrayList<>();

        //when
        for (String jsonString : jsonStrings)
            logs.add(converter.read(jsonString));

        //then
        Assert.assertThat(logs.size(), is(jsonStrings.size()));
        for (AccountLogDTO log : logs)
            Assert.assertThat(log, is(notNullValue()));
    }
}
