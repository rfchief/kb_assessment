package com.kakaobank.profile.consumer.repository;

import com.kakaobank.profile.consumer.model.Account;
import com.kakaobank.profile.consumer.model.AccountAmount;
import com.kakaobank.profile.consumer.model.log.AccountLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryAccountRepository {

    private final Map<Long, HashMap<String, Account>> accountRepository;
    private final Map<String, List<AccountLog>> accountLogRepository;
    private final Map<String, AtomicLong> accountLogSeqRepository;
    private final Map<String, AccountAmount> accountAmountRepository;

    public MemoryAccountRepository() {
        this.accountRepository = new ConcurrentHashMap<>();
        this.accountLogRepository = new ConcurrentHashMap<>();
        this.accountLogSeqRepository = new ConcurrentHashMap<>();
        this.accountAmountRepository = new ConcurrentHashMap<>();
    }
}
