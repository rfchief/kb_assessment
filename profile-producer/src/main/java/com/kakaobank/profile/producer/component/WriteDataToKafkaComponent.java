package com.kakaobank.profile.producer.component;

import java.util.concurrent.ExecutionException;

public interface WriteDataToKafkaComponent {
    void close();

    void write(String content) throws ExecutionException, InterruptedException;
}
