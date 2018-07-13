package com.kakaobank.profile.consumer.model.dto.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakaobank.profile.consumer.model.EventType;

public interface EventLog {

    @JsonProperty("eventType")
    EventType getEventType();

}
