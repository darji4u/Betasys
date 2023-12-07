package com.noxsoft.betasys.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleCreateModel {

    @JsonAlias("moduleID")
    @JsonProperty("MODULE_ID")
    int moduleID;

    @JsonAlias("projectID")
    @JsonProperty("PROJECT_ID")
    int projectID;
    @JsonAlias("moduleName")
    @JsonProperty("MODULE_NAME")
    String moduleName;

    @JsonAlias("parentModule")
    @JsonProperty("PARENT_MODULE")
    int parentModule;
    @JsonAlias("createdOn")
    @JsonProperty("CREATED_ON")
    Date createdOn;
    @JsonAlias("createdBy")
    @JsonProperty("CREATED_BY")
    int createdBy;


    public ModuleCreateModel(int moduleID, int projectID, String moduleName, int parentModule, Date createdOn, int createdBy) {
        this.moduleID = moduleID;
        this.projectID = projectID;
        this.moduleName = moduleName;
        this.parentModule = parentModule;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public ModuleCreateModel(int projectID, String moduleName, int parentModule) {
        this.projectID = projectID;
        this.moduleName = moduleName;
        this.parentModule = parentModule;
    }

    public ModuleCreateModel() {
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public ModuleCreateModel(int projectID, String moduleName, int parentModule, Date createdOn, int createdBy) {
        this.projectID = projectID;
        this.moduleName = moduleName;
        this.parentModule = parentModule;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }



    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getParentModule() {
        return parentModule;
    }

    public void setParentModule(int parentModule) {
        this.parentModule = parentModule;
    }
}
