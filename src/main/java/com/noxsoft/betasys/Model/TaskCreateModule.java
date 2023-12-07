package com.noxsoft.betasys.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskCreateModule {

    public List<Assignee> assignee;
    public int projectID;
    public String moduleID;
    public String priority;
    public String tags;
    public String taskDescription;
    public String taskName;
    public int createdBy;
    public int parentTaskID;
    public LocalDateTime dedline;

    public String remark;


    public TaskCreateModule(List<Assignee> assignee, int projectID, String moduleID, String priority, String tags, String taskDescription, String taskName, int createdBy, int parentTaskID, LocalDateTime dedline,String remark) {
        this.assignee = assignee;
        this.projectID = projectID;
        this.moduleID = moduleID;
        this.priority = priority;
        this.tags = tags;
        this.taskDescription = taskDescription;
        this.taskName = taskName;
        this.createdBy = createdBy;
        this.parentTaskID = parentTaskID;
        this.dedline = dedline;
        this.remark = remark;
    }

    public int getParentTaskID() {
        return parentTaskID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setParentTaskID(int parentTaskID) {
        this.parentTaskID = parentTaskID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public TaskCreateModule() {
    }

    public List<Assignee> getAssignee() {
        return assignee;
    }

    public void setAssignee(List<Assignee> assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getDedline() {
        return dedline;
    }

    public void setDedline(LocalDateTime dedline) {
        this.dedline = dedline;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }




}
