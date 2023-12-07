package com.noxsoft.betasys.Service;

import com.noxsoft.betasys.Dao.UtilityDao;

import com.noxsoft.betasys.Model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UtilityService {

    @Autowired
    UtilityDao utilityDao;

    ResponseModel responseModel = new ResponseModel();

    public List<Map<String,Object>> getDesignation(){
        return utilityDao.getDesignation();
    }


    public List<Map<String,Object>> getSkills(){
        return utilityDao.getSkills();
    }


    public ResponseModel checkUserName(String username,String email) {
        int userExist = utilityDao.checkUserName(username);
        int emailExist = utilityDao.checkEmail(email);
        if(userExist == 1 || emailExist==1){
            setResponseModel("Already Exist","Failed",(userExist==1?"U":"")+(emailExist==1?"E":""));
        }else{
            setResponseModel("This username and email is available","OK","");
        }
        return responseModel;
    }


    public void setResponseModel(String message,String status,String data){
        responseModel.setMessage(message);
        responseModel.setStatus(status);
        responseModel.setData(data);
    }

    public List<Map<String, Object>> getTags() {
        return utilityDao.selectTags();
    }
}
