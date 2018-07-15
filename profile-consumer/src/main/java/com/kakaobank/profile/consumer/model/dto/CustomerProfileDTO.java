package com.kakaobank.profile.consumer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CustomerProfileDTO implements Serializable {
    private static final long serialVersionUID = -1630015913260991552L;

    @JsonProperty("customer_number")
    private long customerNumber;

    @JsonProperty("name")
    private String customerName;

    @JsonProperty("join_dt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDt;

    @JsonProperty("largest_deposit_amount")
    private long largestDepositAmount;

    @JsonProperty("largest_withdrawal_amount")
    private long largestWithdrawalAmount;

    @JsonProperty("largest_transfer_amount")
    private long largestTransferAmount;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getJoinDt() {
        return joinDt;
    }

    public void setJoinDt(LocalDateTime joinDt) {
        this.joinDt = joinDt;
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
}
