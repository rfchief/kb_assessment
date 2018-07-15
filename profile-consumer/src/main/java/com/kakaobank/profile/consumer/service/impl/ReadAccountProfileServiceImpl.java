package com.kakaobank.profile.consumer.service.impl;

import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.EventType;
import com.kakaobank.profile.consumer.model.dto.AccountProfileDTO;
import com.kakaobank.profile.consumer.model.dto.AccountRecordDTO;
import com.kakaobank.profile.consumer.model.log.AccountLog;
import com.kakaobank.profile.consumer.service.ReadAccountProfileService;

import java.util.ArrayList;
import java.util.List;

public class ReadAccountProfileServiceImpl implements ReadAccountProfileService {
    private final AccountDao accountDao;

    public ReadAccountProfileServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public AccountProfileDTO getAccountProfileBy(long customerNumber, String accountNumber, int pageIndex, int pageSize) {
        Account account = accountDao.findByCustomerNumberAndAccountNumber(customerNumber, accountNumber);
        if(account == null)
            return null;

        int offset = pageIndex - 1;
        List<AccountLog> logs = accountDao.findAllLogsByAccountNumber(accountNumber, offset, pageSize);

        List<AccountRecordDTO> depositRecords = null;
        List<AccountRecordDTO> withdrawalRecords = null;
        List<AccountRecordDTO> transferRecords = null;

        for (AccountLog log : logs) {
            AccountRecordDTO recordDTO = new AccountRecordDTO();
            recordDTO.setAmount(log.getAmount());
            recordDTO.setDatetime(log.getDateTime());

            if(log.getEventType() == EventType.DEPOSIT) {
                if(depositRecords == null)
                    depositRecords = new ArrayList<>();

                depositRecords.add(recordDTO);
            }

            if(log.getEventType() == EventType.WITHDRAWAL) {
                if(withdrawalRecords == null)
                    withdrawalRecords = new ArrayList<>();

                withdrawalRecords.add(recordDTO);
            }

            if(log.getEventType() == EventType.TRANSFER) {
                if(transferRecords == null)
                    transferRecords = new ArrayList<>();

                transferRecords.add(recordDTO);
            }
        }

        return getAccountProfileDTO(account, depositRecords, withdrawalRecords, transferRecords);
    }

    private AccountProfileDTO getAccountProfileDTO(Account account, List<AccountRecordDTO> depositRecords, List<AccountRecordDTO> withdrawalRecords, List<AccountRecordDTO> transferRecords) {
        AccountProfileDTO profileDTO = new AccountProfileDTO();
        profileDTO.setCustomerNumber(account.getCustomerNumber());
        profileDTO.setAccountNumber(account.getAccountNumber());
        profileDTO.setCreateDt(account.getCreateDt());
        profileDTO.setBalance(account.getBalance());
        profileDTO.setDepositLogs(depositRecords);
        profileDTO.setWithdrawalLogs(withdrawalRecords);
        profileDTO.setTransferLogs(transferRecords);

        return profileDTO;
    }
}
