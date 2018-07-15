package com.kakaobank.profile.consumer.model.dto;

import java.io.Serializable;

public class EmptyResultDTO implements Serializable {
    private static final long serialVersionUID = 3554350242520379559L;

    private String status;
    private String message;

    public EmptyResultDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
