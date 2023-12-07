package com.noxsoft.betasys.Service;

import com.noxsoft.betasys.Config.JWTService;
import com.noxsoft.betasys.Dao.UserDao;
import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Model.UserCredentialModel;
import com.noxsoft.betasys.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {


    ResponseModel responseModel = new ResponseModel();

    @Autowired
    UserDao userDao;

    PasswordEncoder passwordEncoder;

    @Autowired
    JWTService jwtService;

    @Autowired
    UtilityService utilityService;
    public ResponseModel registerUser(UserModel userModel){
       try{
           passwordEncoder = new BCryptPasswordEncoder();
           responseModel = utilityService.checkUserName(userModel.getUserName(),userModel.getEmail());
           if(!responseModel.getStatus().equalsIgnoreCase("Failed")){
               userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
               int isSuccess = userDao.createUser(userModel);
               if(isSuccess==1){
                   setResponse("Betasys send an email to verify you Email Address : "+userModel.getEmail(),"Success","");
               }else{
                   setResponse("Something Went wrong","Failed","");
               }
           }
       }catch (Exception e){
            System.out.print(e.getMessage());
       }

        return responseModel;
    }

    public UserCredentialModel getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }


    public void setResponse(String Message,String Status,String Data){
        responseModel = new ResponseModel();
        responseModel.setMessage(Message);
        responseModel.setStatus(Status);
        responseModel.setData(Data);
    }


    public List<Map<String, Object>> findUserByUserNameLike(String username) {
        return userDao.findUserByUserNameLike(username);
    }

    public UserModel findUserByUserName(String username) {
         return userDao.findUserByUserName(username);
    }

    public UserModel findUserByToken(String token){
        String username = jwtService.extractUsername(token.substring(7));
        UserModel userModel = userDao.findUserByUserName(username);
        return userModel;
    }
}
