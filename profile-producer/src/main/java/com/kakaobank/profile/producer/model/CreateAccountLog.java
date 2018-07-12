package com.kakaobank.profile.producer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CreateAccountLog implements EventLog, Serializable {
    private static final long serialVersionUID = 6623172258142454990L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("create_dt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDt;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    @Override
    public String toString() {
        return customerNumber + "_" + accountNumber + "_" + createDt.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public EventType getEventType() {
        return EventType.CREATE;
    }
}
