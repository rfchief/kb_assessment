package com.kakaobank.profile.consumer.model;

import java.time.LocalDateTime;

public class Account {

    private long customerNumber;
    private String accountNumber;
    private AccountAmount amount;
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

    public AccountAmount getAmount() {
        return amount;
    }

    public void setAmount(AccountAmount amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public long getLargestDepositAmount() {
        if(amount == null)
            return 0;

        return amount.getLargestDepositAmount();
    }

    public long getLargestWithdrawalAmount() {
        if(amount == null)
            return 0;

        return amount.getLargestWithdrawalAmount();
    }

    public long getLargestTransferAmount() {
        if(amount == null)
            return 0;

        return amount.getLargestTransferAmount();
    }

    @Override
    public String toString() {
        return customerNumber + "___" + accountNumber + "___" + amount.toString() + "___" + createDt.toString();
    }

}
