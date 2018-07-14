package com.kakaobank.profile.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface EventLog {

    @JsonProperty("eventType")
    EventType getEventType();

}
