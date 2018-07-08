package com.kakaobank.profile.producer.model;

import com.kakaobank.profile.producer.util.StringUtil;

import java.time.LocalDateTime;

public class Customer {

    private String id;
    private String name;
    private LocalDateTime joinDt;

    public Customer(String id, String name, LocalDateTime joinDt) {
        this.id = id;
        this.name = name;
        this.joinDt = joinDt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isValid() {
        if(StringUtil.isEmpty(id))
            return false;

        if(StringUtil.isEmpty(name))
            return false;

        if(joinDt == null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return id + "___" + name + "___" + joinDt.toString();
    }
}
