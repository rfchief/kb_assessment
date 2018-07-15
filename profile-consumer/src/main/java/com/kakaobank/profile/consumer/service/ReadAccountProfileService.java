package com.kakaobank.profile.consumer.service;

import com.kakaobank.profile.consumer.model.dto.AccountProfileDTO;

public interface ReadAccountProfileService {
    AccountProfileDTO getAccountProfileBy(long customerNumber, String accountNumber, int pageIndex, int pageSize);
}
