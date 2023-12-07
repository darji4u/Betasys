package com.noxsoft.betasys.Model;

import java.util.List;

public class Assignee{

    public int userID;

    public Assignee(int userID) {
        this.userID = userID;
    }

    public Assignee() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
