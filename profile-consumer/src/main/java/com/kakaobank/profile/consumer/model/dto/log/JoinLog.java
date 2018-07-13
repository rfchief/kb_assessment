package com.kakaobank.profile.consumer.model.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakaobank.profile.consumer.model.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class JoinLog implements EventLog, Serializable {
    private static final long serialVersionUID = -8478670733870979710L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("join_dt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDt;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getJoinDt() {
        return joinDt;
    }

    public void setJoinDt(LocalDateTime joinDt) {
        this.joinDt = joinDt;
    }

    @Override
    public String toString() {
        return customerNumber + "_" + customerName + "_" + joinDt.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public EventType getEventType() {
        return EventType.JOIN;
    }
}
