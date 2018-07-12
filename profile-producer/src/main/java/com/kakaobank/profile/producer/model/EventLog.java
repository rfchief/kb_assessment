package com.kakaobank.profile.producer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EventLog {

    @JsonIgnore
    EventType getEventType();

}
