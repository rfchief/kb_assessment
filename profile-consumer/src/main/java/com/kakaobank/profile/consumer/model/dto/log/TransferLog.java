package com.kakaobank.profile.consumer.model.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kakaobank.profile.consumer.model.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TransferLog implements EventLog, Serializable {
    private static final long serialVersionUID = -8674472335013218190L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("transfer_account_number")
    private String transferAccountNumber;

    @JsonProperty("receive_bank")
    private String receiveBank;

    @JsonProperty("receive_account_number")
    private String receiveAccountNumber;

    @JsonProperty("receive_customer_name")
    private String receiveCustomerName;

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

    public String getTransferAccountNumber() {
        return transferAccountNumber;
    }

    public void setTransferAccountNumber(String transferAccountNumber) {
        this.transferAccountNumber = transferAccountNumber;
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
        return customerNumber + "_"
                + transferAccountNumber + "_"
                + receiveBank + "_"
                + receiveAccountNumber + "_"
                + receiveCustomerName + "_"
                + amount + "_"
                + datetime.toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public EventType getEventType() {
        return EventType.TRANSFER;
    }
}
