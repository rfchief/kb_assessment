package com.kakaobank.profile.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaobank.profile.consumer.component.MessageConverter;
import com.kakaobank.profile.consumer.model.dto.AccountProfileDTO;
import com.kakaobank.profile.consumer.model.dto.EmptyResultDTO;
import com.kakaobank.profile.consumer.service.ReadAccountProfileService;

public class ReadAccountProfileController {

    private final ReadAccountProfileService readAccountProfileService;
    private final MessageConverter messageConverter;

    public ReadAccountProfileController(ReadAccountProfileService readAccountProfileService,
                                        MessageConverter messageConverter) {
        this.readAccountProfileService = readAccountProfileService;
        this.messageConverter = messageConverter;
    }

    public String getAccountProfile(long customerNumber, String accountNumber, int pageIndex, int pageSize) throws JsonProcessingException {
        AccountProfileDTO profile = readAccountProfileService.getAccountProfileBy(customerNumber, accountNumber, pageIndex, pageSize);
        if(profile == null)
            return messageConverter.writeJson(new EmptyResultDTO("NOT FOUND", "Failed to find a account [Customer Number : " + customerNumber + ", Account Number : " + accountNumber + "]"));

        return messageConverter.writeJson(profile);
    }
}
