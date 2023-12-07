package com.noxsoft.betasys.Model;

import java.util.List;

public class CollaboratorModel {
    int userID;
    String userName;
    String userFullName;
    byte[] userProfile;
    List<String> designations;

    public CollaboratorModel(int userID, String userName, String userFullName, byte[] userProfile, List<String> designations) {
        this.userID = userID;
        this.userName = userName;
        this.userFullName = userFullName;
        this.userProfile = userProfile;
        this.designations = designations;
    }

    public CollaboratorModel() {
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

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public byte[] getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(byte[] userProfile) {
        this.userProfile = userProfile;
    }

    public List<String> getDesignations() {
        return designations;
    }

    public void setDesignations(List<String> designations) {
        this.designations = designations;
    }
}
