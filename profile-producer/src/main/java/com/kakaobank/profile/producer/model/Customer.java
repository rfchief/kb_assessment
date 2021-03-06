package com.kakaobank.profile.producer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakaobank.profile.producer.util.StringUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Customer implements Serializable {

    private static final long serialVersionUID = 6734151598372111728L;

    private long number;
    private String name;
    private LocalDateTime joinDt;

    public Customer() {
    }

    public Customer(long number, String name, LocalDateTime joinDt) {
        this.number = number;
        this.name = name;
        this.joinDt = joinDt;
    }

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

    @JsonIgnore
    public boolean isValid() {
        if(number < 0)
            return false;

        if(StringUtil.isEmpty(name))
            return false;

        if(joinDt == null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return number + "___" + name + "___" + joinDt.toString();
    }
}
