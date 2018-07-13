package com.kakaobank.profile.consumer.model;

import java.time.LocalDateTime;

public class Customer {

    private long number;
    private String name;
    private LocalDateTime joinDt;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getJoinDt() {
        return joinDt;
    }

    public void setJoinDt(LocalDateTime joinDt) {
        this.joinDt = joinDt;
    }

    @Override
    public String toString() {
        return number + "___" + name + "___" + joinDt.toString();
    }
}
