package com.noxsoft.betasys.Service;

import com.noxsoft.betasys.Config.JWTService;
import com.noxsoft.betasys.Dao.ProjectDao;
import com.noxsoft.betasys.Dao.UserDao;
import com.noxsoft.betasys.Dao.UtilityDao;
import com.noxsoft.betasys.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {


    @Autowired
    ProjectDao projectDao;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UtilityDao utilityDao;

    public ResponseModel createProject(MultipartFile[] files, ProjectModel projectModel){
        try{
            Long projectID = projectDao.insertProject(projectModel);
            if(projectID!=-1){
                boolean isSuccess = utilityDao.insertDocuments(files,projectID,"PROJECT");
                if(isSuccess){
                    return new ResponseModel("Success","Project Added Successfully","");
                }else {
                    return new ResponseModel("Success","Documents Not Added Successfully","");
                }
            }else{
                return new ResponseModel("Failed","Project Not Added Successfully","");
            }
        }catch (Exception e){
            return new ResponseModel("Failed",e.getMessage(),"");
        }
    }


    public ResponseEntity<List<Map<String,Object>>> getMyProjects(int userID){
        return projectDao.selectMyProject(userID);
    }
    public ResponseEntity<Map<String, Object>> getProjectData(int projectID, int userID) {
        try{
            return projectDao.getProjectData(projectID,userID);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, Object>> getProjectDescription(int projectID, int userID) {
        try{
            return projectDao.getProjectDescription(projectID,userID);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<CollaboratorModel>> getProjectCollaborators(int projectID, String token) {
        try{
            UserModel userModel = userService.findUserByToken(token);
            boolean isProjectMember = projectDao.checkUserMember(userModel.getUserID(),projectID);
            if(isProjectMember){
                return ResponseEntity.of(Optional.of(projectDao.selectProjectCollaborators(projectID)));
            }else{
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public List<String> getUserDesignation(int projectID,int userID){
        return userDao.getUserDesignations(projectID,userID);
    }

    public Map<String, Object> isUserBelongsToPro(int projectID, int userID) {
        List<String> designations = projectDao.getUserDesignationForPro(projectID,userID);
        Map<String,Object> userDesignation = new HashMap<>();
        if(designations!=null&&designations.size()>0){
            userDesignation.put("authentication","Authorized");
            userDesignation.put("designation",designations);
            return userDesignation;
        }else{
            userDesignation.put("authentication","Auauthorized");
            return userDesignation;
        }
    }
}
