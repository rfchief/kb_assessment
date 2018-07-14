package com.kakaobank.profile.consumer.model.dto.log;

import com.kakaobank.profile.consumer.model.EventType;

import java.time.LocalDateTime;

public class AccountLogDTO {
    private long customerNumber;
    private String customerName;
    private String accountNumber;
    private EventType eventType;
    private int amount;
    private String receiveBank;
    private String receiveAccountNumber;
    private String receiveCustomerName;
    private LocalDateTime dateTime;

    public AccountLogDTO(long customerNumber, String customerName, String accountNumber, EventType eventType, int amount, String receiveBank, String receiveAccountNumber, String receiveCustomerName, LocalDateTime dateTime) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.eventType = eventType;
        this.amount = amount;
        this.receiveBank = receiveBank;
        this.receiveAccountNumber = receiveAccountNumber;
        this.receiveCustomerName = receiveCustomerName;
        this.dateTime = dateTime;
    }

    public long getCustomerNumber() {
        return customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getAmount() {
        return amount;
    }

    public String getReceiveBank() {
        return receiveBank;
    }

    public String getReceiveAccountNumber() {
        return receiveAccountNumber;
    }

    public String getReceiveCustomerName() {
        return receiveCustomerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public static class AccountLogDTOBuilder {

        private long customerNumber;
        private String customerName;
        private String accountNumber;
        private EventType eventType;
        private int amount;
        private String receiveBank;
        private String receiveAccountNumber;
        private String receiveCustomerName;
        private LocalDateTime datetime;

        public AccountLogDTOBuilder() {
        }

        public AccountLogDTOBuilder customerNumber(long customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }

        public AccountLogDTOBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public AccountLogDTOBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountLogDTOBuilder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public AccountLogDTOBuilder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public AccountLogDTOBuilder receiveBank(String receiveBank) {
            this.receiveBank = receiveBank;
            return this;
        }

        public AccountLogDTOBuilder receiveAccountNumber(String receiveAccountNumber) {
            this.receiveAccountNumber = receiveAccountNumber;
            return this;
        }

        public AccountLogDTOBuilder receiveCustomerName(String receiveCustomerName) {
            this.receiveCustomerName = receiveCustomerName;
            return this;
        }

        public AccountLogDTOBuilder datatime(LocalDateTime datetime) {
            this.datetime = datetime;
            return this;
        }

        public AccountLogDTO build() {
            return new AccountLogDTO(customerNumber, customerName, accountNumber, eventType,
                                        amount, receiveBank, receiveAccountNumber, receiveCustomerName, datetime);
        }
    }
}
