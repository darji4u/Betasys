package com.noxsoft.betasys.Service;

import com.noxsoft.betasys.Config.JWTService;
import com.noxsoft.betasys.Dao.ModuleDao;
import com.noxsoft.betasys.Dao.UserDao;
import com.noxsoft.betasys.Model.ModuleCreateModel;
import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModuleService {


    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    ModuleDao moduleDao;


    public ResponseModel createModule(String token, ModuleCreateModel moduleCreateModule) {
        try{
            UserModel userModel = userService.findUserByToken(token);
            if(moduleDao.insertProjectModule(moduleCreateModule,userModel.getUserID())){
                return new ResponseModel("Success","Module Created Successfully","");
            }else{
                return new ResponseModel("Failed","Module Not Created Successfully","");
            }

        }catch (Exception e){
                return new ResponseModel("Failed",e.getMessage(),"");
        }
    }

    public ResponseEntity<List<Map<String,Object>>> getAllModules(int projectID, String parentModuleID, String token){
        UserModel userModel = userService.findUserByToken(token);
        return moduleDao.selectAllModule(projectID,parentModuleID,userModel.getUserID());
    }
}
