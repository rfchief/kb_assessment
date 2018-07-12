package com.kakaobank.profile.producer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DepositLog implements EventLog, Serializable {
    private static final long serialVersionUID = -2170347402451013251L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("account_number")
    private String depositAccountNumber;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetime;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getDepositAccountNumber() {
        return depositAccountNumber;
    }

    public void setDepositAccountNumber(String depositAccountNumber) {
        this.depositAccountNumber = depositAccountNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return customerNumber + "_" + depositAccountNumber + "_" + amount + "_" + datetime.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public EventType getEventType() {
        return EventType.DEPOSIT;
    }
}
