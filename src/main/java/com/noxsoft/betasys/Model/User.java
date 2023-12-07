package com.noxsoft.betasys.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class User {
    @JsonProperty("ID")
    private int ID;
    private String userName;
    private byte[] userProfile;
    private String designation;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(byte[] userProfile) {
        this.userProfile = userProfile;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", userName='" + userName + '\'' +
                ", userProfile=" + Arrays.toString(userProfile) +
                ", designation='" + designation + '\'' +
                '}';
    }
}
