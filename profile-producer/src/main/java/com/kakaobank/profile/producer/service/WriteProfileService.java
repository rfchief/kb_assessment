package com.kakaobank.profile.producer.service;

import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.model.EventLog;

public class WriteProfileService {
    private final WriteDataToFileComponent writeDataToFileComponent;

    public WriteProfileService(WriteDataToFileComponent writeDataToFileComponent) {
        this.writeDataToFileComponent = writeDataToFileComponent;
    }

    public boolean write(EventLog eventLog) {
        return false;
    }
}
