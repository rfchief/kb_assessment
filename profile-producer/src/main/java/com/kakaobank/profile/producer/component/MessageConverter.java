package com.kakaobank.profile.producer.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobank.profile.producer.model.EventLog;

import java.io.IOException;

public class MessageConverter {

    private final ObjectMapper objectMapper;

    public MessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convert(EventLog eventLog) throws JsonProcessingException {
        if(eventLog == null)
            throw new IllegalArgumentException("Event log is empty!!!");

        return objectMapper.writeValueAsString(eventLog);
    }

    public EventLog read(String jsonEventLog) throws IOException {
        return objectMapper.readValue(jsonEventLog, EventLog.class);
    }
}
