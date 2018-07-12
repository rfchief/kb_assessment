package com.kakaobank.profile.consumer.model;

public enum EventType {
    JOIN,
    CREATE,
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER;

    public static EventType getRandomWithoutJoinAndCreate() {
        int idx = (int) (Math.random() * values().length);
        return values()[idx <= 1 ? idx + 2 : idx];
    }
}
