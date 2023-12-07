package com.noxsoft.betasys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noxsoft.betasys.Model.CollaboratorModel;
import com.noxsoft.betasys.Model.ModuleCreateModel;
import com.noxsoft.betasys.Model.ProjectModel;
import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Project")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ProjectController {

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/createProject",method = RequestMethod.POST,consumes = "multipart/form-data ")
    public ResponseModel createProject(@RequestParam("files")MultipartFile[] files, @RequestParam("ProjectModel") String requestModel) throws JsonProcessingException {
        ProjectModel projectModel = objectMapper.readValue(requestModel, ProjectModel.class);
        try{
            System.out.print(projectModel.getProjectName());
            return projectService.createProject(files,projectModel);
        }catch (Exception e){
            System.out.print(e.getMessage());
            return new ResponseModel();
        }

    }

    @RequestMapping(value = "/getMyProjects",method = RequestMethod.GET)
    public ResponseEntity<List<Map<String,Object>>> getMyProjects(@RequestParam("userID") int userID){
        return projectService.getMyProjects(userID);
    }

    @RequestMapping(value ="/getProjectData",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProjectData(@RequestParam("projectID") int projectID, @RequestParam("userID") int userID){
        return projectService.getProjectData(projectID,userID);
    }

    @RequestMapping(value ="/getProjectDescription",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProjectDescription(@RequestParam("projectID") int projectID, @RequestParam("userID") int userID){
        return projectService.getProjectDescription(projectID,userID);
    }

    @RequestMapping(value="/getProjectCollaborators",method = RequestMethod.GET)
    public ResponseEntity<List<CollaboratorModel>> getProjectCollaborators(@RequestParam("projectID")int projectID, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return projectService.getProjectCollaborators(projectID,token);
    }


}
