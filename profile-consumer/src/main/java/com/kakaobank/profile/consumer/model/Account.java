package com.kakaobank.profile.consumer.model;

import java.time.LocalDateTime;

public class Account {

    private long customerNumber;
    private String accountNumber;
    private long balance;
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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    @Override
    public String toString() {
        return customerNumber + "___" + accountNumber + "___" + balance + "___" + createDt.toString();
    }
}
