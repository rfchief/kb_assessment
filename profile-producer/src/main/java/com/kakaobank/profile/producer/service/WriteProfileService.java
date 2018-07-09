package com.kakaobank.profile.producer.service;

import com.kakaobank.profile.producer.component.WriteDataToFileComponent;

public class WriteProfileService {
    private final WriteDataToFileComponent writeDataToFileComponent;

    public WriteProfileService(WriteDataToFileComponent writeDataToFileComponent) {
        this.writeDataToFileComponent = writeDataToFileComponent;
    }
}
