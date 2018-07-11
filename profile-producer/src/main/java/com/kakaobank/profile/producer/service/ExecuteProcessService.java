package com.kakaobank.profile.producer.service;

import java.util.Properties;

public interface ExecuteProcessService {

    void doProcess(Properties properties, int maxThreadCount, String logFilePath);
}
