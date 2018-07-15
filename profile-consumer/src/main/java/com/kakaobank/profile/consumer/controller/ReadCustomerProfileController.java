package com.kakaobank.profile.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaobank.profile.consumer.component.MessageConverter;
import com.kakaobank.profile.consumer.model.dto.CustomerProfileDTO;
import com.kakaobank.profile.consumer.model.dto.EmptyResultDTO;
import com.kakaobank.profile.consumer.service.ReadCustomerProfileService;

public class ReadCustomerProfileController {

    private final ReadCustomerProfileService readCustomerProfileService;
    private final MessageConverter messageConverter;

    public ReadCustomerProfileController(ReadCustomerProfileService readCustomerProfileService,
                                         MessageConverter messageConverter) {
        this.readCustomerProfileService = readCustomerProfileService;
        this.messageConverter = messageConverter;
    }

    public String getCustomerProfile(long customerNumber) throws JsonProcessingException {
        CustomerProfileDTO profile = readCustomerProfileService.getCustomerProfileBy(customerNumber);
        if(profile == null)
            return messageConverter.writeJson(new EmptyResultDTO("NOT FOUND", "Failed to find a customer [Customer Number : " + customerNumber + "]"));

        return messageConverter.writeJson(profile);
    }
}
