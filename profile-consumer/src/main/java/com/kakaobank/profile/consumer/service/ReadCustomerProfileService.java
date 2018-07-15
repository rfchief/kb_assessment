package com.kakaobank.profile.consumer.service;

import com.kakaobank.profile.consumer.model.dto.CustomerProfileDTO;

public interface ReadCustomerProfileService {
    CustomerProfileDTO getCustomerProfileBy(long customerNumber);
}
