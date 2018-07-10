package com.kakaobank.profile.producer.service;

import com.kakaobank.profile.producer.model.EventLog;

import java.io.IOException;
import java.util.List;

public interface WriteProfileService {

    boolean write(EventLog eventLog);

    List<Boolean> write(List<EventLog> logs);

    void open() throws IOException;

    void close() throws IOException;

}
