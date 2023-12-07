package com.noxsoft.betasys.Service;

import com.noxsoft.betasys.Dao.TaskDao;
import com.noxsoft.betasys.Dao.UtilityDao;
import com.noxsoft.betasys.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Autowired
    UtilityDao utilityDao;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    public ResponseEntity<ResponseModel> createTask(MultipartFile[] files, TaskCreateModule taskCreateModule){
        try{
            Long taskID = taskDao.insertTask(taskCreateModule);
            if(taskID!=-1){
                boolean isSuccess = utilityDao.insertDocuments(files,taskID,"TASK");
                if(isSuccess){
                    return ResponseEntity.of(Optional.of(new ResponseModel("Success","Task created successfully","")));
                }else {
                    return ResponseEntity.of(Optional.of(new ResponseModel("Success","Documents not added successfully","")));
                }
            }else{
                return ResponseEntity.of(Optional.of(new ResponseModel("Failed","Task not created successfully","")));
            }
        }catch (Exception e){
            return ResponseEntity.of(Optional.of(new ResponseModel("Failed",e.getMessage(),"")));
        }
    }

    public ResponseEntity<Map<String,Object>> getTasks(int projectID, int moduleID, String auth) {
        Map<String,Object> taskList = new HashMap<>();
        try {
            UserModel userModel = userService.findUserByToken(auth);
//            taskList.put("userDesignations",projectService.getUserDesignation(projectID,userModel.getUserID()));
            taskList.put("taskList",taskDao.getTasks(projectID,moduleID,userModel.getUserID()));
            return ResponseEntity.of(Optional.of(taskList));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public CustomResponse<Map<String, Object>> getTaskDetail(int projectID, int moduleID, int taskID) {

        try{

            return taskDao.selectTaskDetail(projectID,moduleID,taskID);

        }catch (Exception e){

            CustomResponse<Map<String,Object>> exceptionResponse = new CustomResponse<>();
            exceptionResponse.setMessage(e.getMessage());
            exceptionResponse.setStatus("Failed");
            return exceptionResponse;

        }


    }


    public List<Map<String, Object>> getTaskForAction(int userID, int projectID, int taskID) {

//        List<Map<String,Object>> assigneeResponseData = taskDao.selectForTaskAssigneeDetail(taskID);
//
//        List<Map<String,Object>> assigneeData = new ArrayList<>();
//        for(Map<String,Object> assignee : assigneeResponseData){
//            String[] designation = String.valueOf(assignee.get("designation")).split(",");
//            for(String des : designation){
//                Map<String,Object> assigneeObj = new HashMap<>();
//                assigneeObj.putAll(assignee);
//                assigneeData.add(assigneeObj);
//            }
//        }
        return taskDao.selectTasksForAction(projectID,taskID,userID);
    }

    public void updateTaskAction(String auth, TaskActionRequestModel taskActionRequestModel) {

        UserModel userModel = userService.findUserByToken(auth);
        taskDao.updateTaskAction(userModel.getUserID(),taskActionRequestModel);

    }
}
