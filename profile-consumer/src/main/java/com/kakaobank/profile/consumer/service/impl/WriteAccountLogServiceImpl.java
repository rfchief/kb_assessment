package com.kakaobank.profile.consumer.service.impl;

import com.kakaobank.profile.consumer.component.MessageConverter;
import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.Customer;
import com.kakaobank.profile.consumer.model.EventType;
import com.kakaobank.profile.consumer.model.dto.log.AccountLogDTO;
import com.kakaobank.profile.consumer.model.log.AccountLog;
import com.kakaobank.profile.consumer.service.WriteAccountLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteAccountLogServiceImpl implements WriteAccountLogService {
    private final static Logger logger = LoggerFactory.getLogger(WriteAccountLogServiceImpl.class);

    private final CustomerDao customerDao;
    private final AccountDao accountDao;
    private final MessageConverter messageConverter;

    public WriteAccountLogServiceImpl(CustomerDao customerDao,
                                      AccountDao accountDao,
                                      MessageConverter messageConverter) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
        this.messageConverter = messageConverter;
    }

    @Override
    public void write(String content) {
        if (content == null || content == "")
            return;

        AccountLogDTO accountLogDTO = messageConverter.read(content);
        if(accountLogDTO == null) {
            logger.error("Failed to convert to AccountLogDTO. [JSON : " + content + "]");
            return;
        }

        applyAccountLog(accountLogDTO);
    }

    private void applyAccountLog(AccountLogDTO accountLogDTO) {
        EventType eventTypeOfLog = accountLogDTO.getEventType();
        if(eventTypeOfLog == EventType.JOIN)
            createCustomer(accountLogDTO);
        else if(eventTypeOfLog == EventType.CREATE)
            createAccount(accountLogDTO);
        else
            saveAccountLog(accountLogDTO);
    }

    private void saveAccountLog(AccountLogDTO accountLogDTO) {
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountNumber(accountLogDTO.getAccountNumber());
        accountLog.setEventType(accountLogDTO.getEventType());
        accountLog.setAmount(accountLogDTO.getAmount());
        accountLog.setReceiveCustomerName(accountLogDTO.getReceiveCustomerName());
        accountLog.setReceiveAccountNumber(accountLogDTO.getReceiveAccountNumber());
        accountLog.setReceiveBank(accountLogDTO.getReceiveBank());
        accountLog.setDateTime(accountLogDTO.getDateTime());

        accountDao.insert(accountLog);
    }

    private void createAccount(AccountLogDTO accountLogDTO) {
        Account account = new Account();
        account.setCustomerNumber(accountLogDTO.getCustomerNumber());
        account.setAccountNumber(accountLogDTO.getAccountNumber());
        account.setCreateDt(accountLogDTO.getDateTime());
        account.setAmount(new AccountAmount(accountLogDTO.getAccountNumber(),0, 0, 0, 0));

        accountDao.insert(account);
    }

    private void createCustomer(AccountLogDTO accountLogDTO) {
        Customer customer = new Customer();
        customer.setNumber(accountLogDTO.getCustomerNumber());
        customer.setName(accountLogDTO.getCustomerName());
        customer.setJoinDt(accountLogDTO.getDateTime());

        customerDao.insert(customer);
    }
}
