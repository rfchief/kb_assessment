package com.kakaobank.profile.consumer.util;

import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.EventType;
import com.kakaobank.profile.consumer.model.log.AccountLog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TestDataFactory {

    public static String getAccountNumber() {
        return String.valueOf(ConsumerUtil.getRandomNumber(999999999));
    }

    public static Account getAccount() {
        Account account = new Account();
        account.setCustomerNumber(ConsumerUtil.getRandomNumber(1000));
        account.setAccountNumber(String.valueOf(ConsumerUtil.getRandomNumber(100000000)));
        account.setAmount(new AccountAmount(account.getAccountNumber()));

        return account;
    }

    public static AccountLog getDepositLog(long seq, String accountNumber) {
        AccountLog given = new AccountLog();
        given.setSeq(seq);
        given.setAccountNumber(accountNumber);
        given.setAmount(ConsumerUtil.getRandomNumber(100));
        given.setEventType(EventType.DEPOSIT);
        given.setDateTime(LocalDateTime.now());

        return given;
    }

    public static AccountLog getWithdrawalLog(long seq, String accountNumber) {
        AccountLog given = new AccountLog();
        given.setSeq(seq);
        given.setAccountNumber(accountNumber);
        given.setEventType(EventType.WITHDRAWAL);
        given.setAmount(ConsumerUtil.getRandomNumber(1000));
        given.setDateTime(LocalDateTime.now());

        return given;
    }

    public static AccountLog getTransferLog(long seq, String accountNumber) {
        AccountLog given = new AccountLog();
        given.setSeq(seq);
        given.setAccountNumber(accountNumber);
        given.setEventType(EventType.TRANSFER);
        given.setAmount(ConsumerUtil.getRandomNumber(10000));
        given.setReceiveBank(StringUtil.randomString(7, true));
        given.setReceiveAccountNumber(getAccountNumber());
        given.setReceiveCustomerName(StringUtil.randomString(10, true));
        given.setDateTime(LocalDateTime.now());

        return given;
    }

    public static List<String> getJsonAccountLogs(String filePath) throws IOException {
        return FileReader.readActualContent(filePath);
    }

}
