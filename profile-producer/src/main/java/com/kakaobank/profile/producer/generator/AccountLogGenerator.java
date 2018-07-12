package com.kakaobank.profile.producer.generator;

import com.kakaobank.profile.producer.model.*;
import com.kakaobank.profile.producer.util.StringUtil;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class AccountLogGenerator {
    private final int MAX_BOUND_FOR_AMOUNT;
    private final int MAX_BOUND;

    public AccountLogGenerator(int maxBoundForAmount) {
        this.MAX_BOUND_FOR_AMOUNT = maxBoundForAmount;
        this.MAX_BOUND = maxBoundForAmount * 99;
    }

    public EventLog doGenerate(Customer customer) {
        return doGenerate(customer, getRandomEventTypeWithoutJoinAndCreate());
    }

    public EventLog doGenerate(Customer customer, EventType eventType) {
        if(customer == null)
            throw new IllegalArgumentException("Customer info is empty!!!!");

        LocalDateTime eventTime = LocalDateTime.now();

        return createEventLog(customer, eventType, eventTime);
    }

    private EventLog createEventLog(Customer customer, EventType eventType, LocalDateTime eventTime) {
        if(eventType == EventType.JOIN)
            return getJoinLog(customer);

        if(eventType == EventType.CREATE)
            return getCreateAccountLog(customer);

        if(eventType == EventType.DEPOSIT)
            return getDepositLog(customer);

        if(eventType == EventType.WITHDRAWAL)
            return getWithdrawalLog(customer);

        if(eventType == EventType.TRANSFER)
            return getTransferLog(customer);

        return null;
    }

    private TransferLog getTransferLog(Customer customer) {
        TransferLog transferLog = new TransferLog();
        transferLog.setCustomerNumber(customer.getNumber());
        transferLog.setTransferAccountNumber(String.valueOf(getRandomNumber(MAX_BOUND)));
        transferLog.setReceiveBank(getRandomString(5));
        transferLog.setReceiveAccountNumber(String.valueOf(getRandomNumber(MAX_BOUND)));
        transferLog.setReceiveCustomerName(getRandomString(7));
        transferLog.setAmount(getRandomAmount());
        transferLog.setDatetime(LocalDateTime.now());
        return transferLog;
    }

    private WithdrawalLog getWithdrawalLog(Customer customer) {
        WithdrawalLog withdrawalLog = new WithdrawalLog();
        withdrawalLog.setCustomerNumber(customer.getNumber());
        withdrawalLog.setAmount(getRandomAmount());
        withdrawalLog.setWithdrawalAccountNumber(String.valueOf(getRandomNumber(MAX_BOUND)));
        withdrawalLog.setDatetime(LocalDateTime.now());
        return withdrawalLog;
    }

    private DepositLog getDepositLog(Customer customer) {
        DepositLog depositLog = new DepositLog();
        depositLog.setCustomerNumber(customer.getNumber());
        depositLog.setAmount(getRandomAmount());
        depositLog.setDepositAccountNumber(String.valueOf(getRandomNumber(MAX_BOUND)));
        depositLog.setDatetime(LocalDateTime.now());
        return depositLog;
    }

    private CreateAccountLog getCreateAccountLog(Customer customer) {
        CreateAccountLog createAccountLog = new CreateAccountLog();
        createAccountLog.setCustomerNumber(customer.getNumber());
        createAccountLog.setAccountNumber(String.valueOf(getRandomNumber(MAX_BOUND)));
        createAccountLog.setCreateDt(LocalDateTime.now());
        return createAccountLog;
    }

    private JoinLog getJoinLog(Customer customer) {
        JoinLog joinLog = new JoinLog();
        joinLog.setCustomerName(customer.getName());
        joinLog.setCustomerNumber(customer.getNumber());
        joinLog.setJoinDt(LocalDateTime.now());
        return joinLog;
    }

    private int getRandomAmount() {
        return getRandomNumber(MAX_BOUND_FOR_AMOUNT);
    }

    private int getRandomNumber(int maxBound) {
        return ThreadLocalRandom.current().nextInt(maxBound);
    }

    private String getRandomString(int maxLength) {
        return StringUtil.randomString(maxLength, true);
    }


    private EventType getRandomEventTypeWithoutJoinAndCreate() {
        return EventType.getRandomWithoutJoinAndCreate();
    }

}
