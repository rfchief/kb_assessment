package com.kakaobank.profile.producer.model;

public enum EventType {
    JOIN,
    CREATE,
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER;

    public static EventType getRandom() {
        int idx = (int) (Math.random() * values().length);
        return values()[idx <= 1 ? idx + 2 : idx];
    }
}
