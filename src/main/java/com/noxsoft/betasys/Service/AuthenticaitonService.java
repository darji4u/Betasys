package com.noxsoft.betasys.Service;

import com.noxsoft.betasys.Config.JWTService;
import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Model.TokenResponse;
import com.noxsoft.betasys.Model.UserCredentialModel;
import com.noxsoft.betasys.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticaitonService {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    public TokenResponse tokenResponse;



    @Autowired
    JWTService jwtService;

    public TokenResponse isAuthorized(UserCredentialModel userCredentialModel){
        try
        {
            String username = userCredentialModel.getUserName();
            String password = userCredentialModel.getPassword();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            if (authentication.isAuthenticated()) {
                UserModel userModel = userService.findUserByUserName(username);
                String userToken = jwtService.generateToken(userModel);
                Map<String,Object> userData = new HashMap<>();
                userData.put("userID",userModel.getUserID());
                userData.put("userName",userModel.getUserName());
                userData.put("userProfile",userModel.getUserProfile());
                tokenResponse = new TokenResponse("Success", "Authorized",userData, userToken);
                return tokenResponse;
            } else {
                tokenResponse = new TokenResponse("Failed", "UnAuthorized",null, "");
                return tokenResponse;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return tokenResponse = new TokenResponse("Failed",e.getMessage(),null,"");
        }
    }


}
