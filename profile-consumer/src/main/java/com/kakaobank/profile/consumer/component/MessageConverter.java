package com.kakaobank.profile.consumer.component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MessageConverter {

    private final ObjectMapper objectMapper;

    public MessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

//    public String convert(EventLog eventLog) throws JsonProcessingException {
//        if(eventLog == null)
//            throw new IllegalArgumentException("Event log is empty!!!");
//
//        return objectMapper.writeValueAsString(eventLog);
//    }

    public <T> T read(String jsonEventLog, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonEventLog, valueType);
    }
}
