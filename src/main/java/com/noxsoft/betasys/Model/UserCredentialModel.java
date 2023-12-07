package com.noxsoft.betasys.Model;

public class UserCredentialModel {
    String userName;
    String password;

    public UserCredentialModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserCredentialModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
