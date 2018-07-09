package com.kakaobank.profile.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaobank.profile.producer.component.MessageConverter;
import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.model.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteProfileService {
    private final Logger logger = LoggerFactory.getLogger(WriteProfileService.class);

    private final MessageConverter messageConverter;
    private final WriteDataToFileComponent writeDataToFileComponent;

    public WriteProfileService(WriteDataToFileComponent writeDataToFileComponent, MessageConverter messageConverter) {
        this.writeDataToFileComponent = writeDataToFileComponent;
        this.messageConverter = messageConverter;
    }

    public boolean write(EventLog eventLog) {
        if(eventLog == null || !eventLog.isValid())
            throw new IllegalArgumentException("EventLog is not Valid");

        try {
            String jsonEventLog = messageConverter.convert(eventLog);
            writeDataToFileComponent.write(jsonEventLog);

            return true;
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    public List<Boolean> write(List<EventLog> logs) {
        List<Boolean> results = new ArrayList<>();
        for (EventLog log : logs)
            results.add(write(log));

        return results;
    }

    public void open() throws IOException {
        writeDataToFileComponent.open();
    }

    public void close() throws IOException {
        writeDataToFileComponent.close();
    }
}
