package com.kakaobank.profile.producer.generator;

import com.kakaobank.profile.producer.model.Customer;
import com.kakaobank.profile.producer.model.EventLog;
import com.kakaobank.profile.producer.model.EventType;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class AccountLogGenerator {
    private final int MAX_BOUND_FOR_AMOUNT;

    public AccountLogGenerator(int maxBoundForAmount) {
        this.MAX_BOUND_FOR_AMOUNT = maxBoundForAmount;
    }

    public EventLog doGenerate(Customer customer) {
        return doGenerate(customer, getRandomEventType());
    }

    public EventLog doGenerate(Customer customer, EventType eventType) {
        if(customer == null)
            throw new IllegalArgumentException("Customer info is empty!!!!");

        int amount = getRandomAmount();
        LocalDateTime eventTime = LocalDateTime.now();

        return createEventLog(customer, eventType, eventTime, amount);
    }

    public EventLog doGenerateJoinEvent(Customer customer) {
        return doGenerate(customer, EventType.JOIN);
    }

    private EventLog createEventLog(Customer customer, EventType eventType, LocalDateTime eventTime, int amount) {
        EventLog eventLog = new EventLog();
        eventLog.setCustomer(customer);
        eventLog.setEventType(eventType);
        eventLog.setEventTime(eventTime);
        eventLog.setAmount(amount);

        return eventLog;
    }

    private int getRandomAmount() {
        return ThreadLocalRandom.current().nextInt(MAX_BOUND_FOR_AMOUNT);
    }

    private EventType getRandomEventType() {
        return EventType.getRandom();
    }

}
