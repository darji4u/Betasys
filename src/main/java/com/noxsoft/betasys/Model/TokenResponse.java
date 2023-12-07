package com.noxsoft.betasys.Model;

import java.util.Map;

public class TokenResponse {
    String status;
    String message;
    Map<String,Object> data;
    String token;

    public TokenResponse() {
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenResponse(String status, String message, Map<String, Object> data, String token) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
    }
}
