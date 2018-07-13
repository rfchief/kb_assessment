package com.kakaobank.profile.consumer.model;

import com.kakaobank.profile.consumer.model.log.AccountLog;

public class AccountAmount {

    private String accountNumber;
    private long balance;
    private long largestDepositAmount;
    private long largestWithdrawalAmount;
    private long largestTransferAmount;

    public AccountAmount() {
        this(null);
    }

    public AccountAmount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.largestDepositAmount = 0;
        this.largestWithdrawalAmount = 0;
        this.largestTransferAmount = 0;
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

    public long getLargestDepositAmount() {
        return largestDepositAmount;
    }

    public void setLargestDepositAmount(long largestDepositAmount) {
        this.largestDepositAmount = largestDepositAmount;
    }

    public long getLargestWithdrawalAmount() {
        return largestWithdrawalAmount;
    }

    public void setLargestWithdrawalAmount(long largestWithdrawalAmount) {
        this.largestWithdrawalAmount = largestWithdrawalAmount;
    }

    public long getLargestTransferAmount() {
        return largestTransferAmount;
    }

    public void setLargestTransferAmount(long largestTransferAmount) {
        this.largestTransferAmount = largestTransferAmount;
    }

    public void update(AccountLog accountLog) {
        EventType eventType = accountLog.getEventType();
        int amount = accountLog.getAmount();

        if(eventType == EventType.DEPOSIT) {
            updateLargestDepositAmount(amount);
            updateBalance(amount);
        }

        if(eventType == EventType.WITHDRAWAL) {
            updateLargestWithdrawalAmount(amount);
            updateBalance(amount * -1);
        }

        if(eventType == EventType.TRANSFER) {
            updateLargestTransferAmount(amount);
            updateBalance(amount * -1);
        }
    }

    private void updateLargestTransferAmount(int amount) {
        if(largestTransferAmount < amount)
            this.largestTransferAmount = amount;
    }

    private void updateLargestWithdrawalAmount(int amount) {
        if(largestWithdrawalAmount < amount)
            this.largestWithdrawalAmount = amount;
    }

    private void updateLargestDepositAmount(int amount) {
        if(largestDepositAmount < amount)
            this.largestDepositAmount = amount;
    }

    private void updateBalance(int amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return balance + "___" + largestDepositAmount + "___" + largestWithdrawalAmount + "___" + largestTransferAmount;
    }

}
