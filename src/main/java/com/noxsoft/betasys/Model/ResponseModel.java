package com.noxsoft.betasys.Model;

public class ResponseModel {
    String Status;
    String Message;
    String Data;

    public ResponseModel(String status, String message, String data) {
        Status = status;
        Message = message;
        Data = data;
    }

    public ResponseModel() {
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

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
