package com.noxsoft.betasys.Model;

public class CustomResponse<T> {
    String Status;
    String Message;

    int StatusCode;
    T Data;

    public CustomResponse(String status, String message, T data) {
        Status = status;
        Message = message;
        Data = data;
    }

    public CustomResponse(String status, String message, int statusCode, T data) {
        Status = status;
        Message = message;
        StatusCode = statusCode;
        Data = data;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public CustomResponse() {
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
