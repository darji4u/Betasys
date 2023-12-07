package com.noxsoft.betasys.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {

    @JsonAlias("userID")
    @JsonProperty("USER_ID")
    int userID;
    @JsonAlias("userName")
    @JsonProperty("USER_NAME")
    String userName;
    @JsonAlias("fullName")
    @JsonProperty("FULL_NAME")
    String fullName;
    @JsonAlias("email")
    @JsonProperty("EMAIL")
    String email;
    @JsonAlias("gender")
    @JsonProperty("GENDER")
    String gender;
    @JsonAlias("password")
    @JsonProperty("USER_PASSWORD")
    String password;
    @JsonAlias("designation")
    @JsonProperty("DESIGNATION")
    String designation;
    @JsonAlias("userProfile")
    @JsonProperty("USER_PROFILE")
    byte[] userProfile;
    @JsonAlias("skills")
    @JsonProperty("SKILLS")
    String skills;
    @JsonProperty("IS_ENABLE")
    String isEnable;
    @JsonProperty("USER_STATUS")
    String status;

    String message;
    String response;


    public byte[] getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(byte[] userProfile) {
        this.userProfile = userProfile;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String isEnable() {
        return isEnable;
    }

    public void setEnable(String enable) {
        isEnable = enable;
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
