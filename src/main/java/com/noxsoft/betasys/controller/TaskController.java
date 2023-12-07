package com.noxsoft.betasys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noxsoft.betasys.Model.*;
import com.noxsoft.betasys.Service.ProjectService;
import com.noxsoft.betasys.Service.TaskService;
import com.noxsoft.betasys.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Task")
@CrossOrigin(origins = {"http://localhost:3000"})
public class TaskController{


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;


    @RequestMapping(value = "/createTask",method = RequestMethod.POST,consumes = "multipart/form-data ")
    public ResponseEntity<ResponseModel> createTask(@RequestParam("files") MultipartFile[] files,
                                                    @RequestParam("TaskModel") String requestModel,
                                                    HttpServletRequest httpRequest) throws JsonProcessingException{

            UserModel userModel = userService.findUserByToken(httpRequest.getHeader("Authorization"));
            TaskCreateModule createTaskModal = objectMapper.readValue(requestModel, TaskCreateModule.class);
            createTaskModal.setCreatedBy(userModel.getUserID());
            return taskService.createTask(files,createTaskModal);
    }


    @RequestMapping(value = "/getTasks",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getTasks(@RequestParam("projectID") int projectID,@RequestParam("moduleID") int moduleID,HttpServletRequest httpServletRequest){
        String auth = httpServletRequest.getHeader("Authorization");
        return taskService.getTasks(projectID,moduleID,auth);
    }


    @RequestMapping(value="/getTaskDetails",method = RequestMethod.GET)
    public CustomResponse<Map<String,Object>> getTaskDetail(@RequestParam("projectID") int projectID, @RequestParam("moduleID") int moduleID, @RequestParam("taskID")int taskID){
        try{
            return taskService.getTaskDetail(projectID,moduleID,taskID);
        }catch (Exception e){
            CustomResponse<Map<String,Object>> customResponse = new CustomResponse<>();
            customResponse.setMessage(e.getMessage());
            customResponse.setStatus("Failed");
            Map<String,Object> body = new HashMap<>();
            body.put("name","hello");
            customResponse.setData(body);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customResponse).getBody();
        }
    }

    @RequestMapping(value = "/getTasksForAction",method = RequestMethod.GET)
    public CustomResponse<List<Map<String, Object>>> getTaskForAction(
                                 @RequestParam("projectID") int projectID,
                                 @RequestParam("taskID") int taskID,
                                 HttpServletRequest request)
    {

        CustomResponse<List<Map<String,Object>>> response = new CustomResponse<>();
        try{

            String auth = request.getHeader("Authorization");
            UserModel userModel = userService.findUserByToken(auth);

            Map<String,Object> userAuthForProject = projectService.isUserBelongsToPro(projectID,userModel.getUserID());
            if(userAuthForProject.get("authentication").equals("Authorized")){
                List<Map<String,Object>> actionData = taskService.getTaskForAction(userModel.getUserID(), projectID,taskID);
                if(actionData!=null && actionData.size()>0){
                    response.setStatus("Success");
                    response.setMessage("");
                    response.setData(actionData);
                }else{
                    response.setStatus("Failed");
                    response.setMessage("No Data Found");
                    response.setData(new ArrayList<>());
                }
                return response;
            }else{
                response.setStatus("Failed");
                response.setMessage("Auauthorized Access");
                return response;
            }

        }catch (Exception e){
            response.setStatus("Failed");
            response.setMessage("Auauthorized Access");
            return response;
        }

    }


    @RequestMapping(value = "/setTaskAction", method = RequestMethod.POST)
    public CustomResponse<Map<String, Object>> setTaskAction(@RequestBody TaskActionRequestModel taskActionRequestModel, HttpServletRequest request){

        String auth = request.getHeader("Authorization");
        CustomResponse<Map<String,Object>> response = new CustomResponse<>();

        try{
            taskService.updateTaskAction(auth,taskActionRequestModel);
            response.setStatus("Success");
            return response;
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setStatus("Failed");
            return response;
        }

    }


}
