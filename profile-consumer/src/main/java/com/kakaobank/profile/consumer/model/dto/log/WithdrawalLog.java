package com.kakaobank.profile.consumer.model.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakaobank.profile.consumer.model.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WithdrawalLog implements EventLog, Serializable {
    private static final long serialVersionUID = -7384312825431692598L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("account_number")
    private String withdrawalAccountNumber;

    @JsonProperty("amount")
    private int amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetime;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getWithdrawalAccountNumber() {
        return withdrawalAccountNumber;
    }

    public void setWithdrawalAccountNumber(String withdrawalAccountNumber) {
        this.withdrawalAccountNumber = withdrawalAccountNumber;
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
        return customerNumber + "_" + withdrawalAccountNumber + "_" + amount + "_" + datetime.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public EventType getEventType() {
        return EventType.WITHDRAWAL;
    }
}
