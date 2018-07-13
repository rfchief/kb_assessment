package com.kakaobank.profile.consumer.model.log;

import com.kakaobank.profile.consumer.model.EventType;

import java.time.LocalDateTime;

public class AccountLog {
    private long seq;
    private String accountNumber;
    private EventType eventType;
    private int amount;
    private String receiveBank;
    private String receiveAccountNumber;
    private String receiveCustomerName;
    private LocalDateTime dateTime;

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReceiveBank() {
        return receiveBank;
    }

    public void setReceiveBank(String receiveBank) {
        this.receiveBank = receiveBank;
    }

    public String getReceiveAccountNumber() {
        return receiveAccountNumber;
    }

    public void setReceiveAccountNumber(String receiveAccountNumber) {
        this.receiveAccountNumber = receiveAccountNumber;
    }

    public String getReceiveCustomerName() {
        return receiveCustomerName;
    }

    public void setReceiveCustomerName(String receiveCustomerName) {
        this.receiveCustomerName = receiveCustomerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
