package com.kakaobank.profile.consumer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class AccountProfileDTO implements Serializable {
    private static final long serialVersionUID = -2872456249672770218L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("create_dt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDt;

    @JsonProperty("balance")
    private long balance;

    @JsonProperty("deposits")
    private List<AccountRecordDTO> depositLogs;

    @JsonProperty("withdrawals")
    private List<AccountRecordDTO> withdrawalLogs;

    @JsonProperty("transfers")
    private List<AccountRecordDTO> transferLogs;

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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<AccountRecordDTO> getDepositLogs() {
        return depositLogs;
    }

    public void setDepositLogs(List<AccountRecordDTO> depositLogs) {
        this.depositLogs = depositLogs;
    }

    public List<AccountRecordDTO> getWithdrawalLogs() {
        return withdrawalLogs;
    }

    public void setWithdrawalLogs(List<AccountRecordDTO> withdrawalLogs) {
        this.withdrawalLogs = withdrawalLogs;
    }

    public List<AccountRecordDTO> getTransferLogs() {
        return transferLogs;
    }

    public void setTransferLogs(List<AccountRecordDTO> transferLogs) {
        this.transferLogs = transferLogs;
    }
}
