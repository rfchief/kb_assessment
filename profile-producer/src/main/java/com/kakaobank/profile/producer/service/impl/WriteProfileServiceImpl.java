package com.kakaobank.profile.producer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kakaobank.profile.producer.component.WriteDataToFileComponent;
import com.kakaobank.profile.producer.component.WriteDataToKafkaComponent;
import com.kakaobank.profile.producer.component.impl.MessageConverter;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.service.WriteProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WriteProfileServiceImpl implements WriteProfileService {
    private final Logger logger = LoggerFactory.getLogger(WriteProfileServiceImpl.class);

    private final MessageConverter messageConverter;
    private final WriteDataToFileComponent writeDataToFileComponent;
    private final WriteDataToKafkaComponent writeDataToKafkaComponent;

    public WriteProfileServiceImpl(WriteDataToFileComponent writeDataToFileComponent,
                                   WriteDataToKafkaComponent writeDataToKafkaComponent,
                                   MessageConverter messageConverter) {
        this.writeDataToFileComponent = writeDataToFileComponent;
        this.writeDataToKafkaComponent = writeDataToKafkaComponent;
        this.messageConverter = messageConverter;
    }

    public boolean write(EventLog eventLog) {
        if(eventLog == null)
            throw new IllegalArgumentException("EventLog is not Valid");

        try {
            String jsonEventLog = messageConverter.convert(eventLog);
            boolean isSuccessToKafka = writeToKafka(jsonEventLog);
            boolean isSuccessToFile = writeToFile(jsonEventLog);

            return isSuccessToKafka && isSuccessToFile ? true : false;
        } catch (JsonProcessingException e) {
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
        writeDataToKafkaComponent.close();
    }

    private boolean writeToFile(String jsonEventLog) {
        try {
            writeDataToFileComponent.write(jsonEventLog);
            return true;
        } catch (IOException e) {
            logger.error(String.format("Fail to write content on file. [message : %s, json : %s]", e.getMessage(), jsonEventLog));
        }

        return false;
    }

    private boolean writeToKafka(String jsonEventLog) {
        try {
            writeDataToKafkaComponent.write(jsonEventLog);
            return true;
        } catch (ExecutionException e) {
            logger.error(String.format("Failed to write content on kafka. [Message : %s, json : %s]", e.getMessage(), jsonEventLog));
        } catch (InterruptedException e) {
            logger.error(String.format("Failed to write content on kafka. [Message : %s, json : %s]", e.getMessage(), jsonEventLog));
        }

        return false;
    }
}
