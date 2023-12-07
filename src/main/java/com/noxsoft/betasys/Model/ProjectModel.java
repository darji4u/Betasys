package com.noxsoft.betasys.Model;

import java.util.Date;
import java.util.List;

public class ProjectModel {
    public String projectName;
    public String description;
    public List<User> projectManagers;
    public List<User> baTeam;
    public List<User> developerTeam;
    public List<User> qaList;
    int createdBy;

    public ProjectModel(String projectName, String description, List<User> projectManagers, List<User> baTeam, List<User> developerTeam, List<User> qaList, int createdBy) {
        this.projectName = projectName;
        this.description = description;
        this.projectManagers = projectManagers;
        this.baTeam = baTeam;
        this.developerTeam = developerTeam;
        this.qaList = qaList;
        this.createdBy = createdBy;
    }

    public ProjectModel() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getProjectManagers() {
        return projectManagers;
    }

    public void setProjectManagers(List<User> projectManagers) {
        this.projectManagers = projectManagers;
    }

    public List<User> getBaTeam() {
        return baTeam;
    }

    public void setBaTeam(List<User> baTeam) {
        this.baTeam = baTeam;
    }

    public List<User> getDeveloperTeam() {
        return developerTeam;
    }

    public void setDeveloperTeam(List<User> developerTeam) {
        this.developerTeam = developerTeam;
    }

    public List<User> getQaList() {
        return qaList;
    }

    public void setQaList(List<User> qaList) {
        this.qaList = qaList;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}
