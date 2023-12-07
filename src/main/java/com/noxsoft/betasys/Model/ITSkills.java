package com.noxsoft.betasys.Model;


public class ITSkills {

    int ID;
    String skill;

    public ITSkills(int ID, String skill) {
        this.ID = ID;
        this.skill = skill;
    }

    public ITSkills() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
