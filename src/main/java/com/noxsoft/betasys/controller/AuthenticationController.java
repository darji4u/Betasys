package com.noxsoft.betasys.controller;

import com.noxsoft.betasys.Config.JWTService;
import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Model.TokenResponse;
import com.noxsoft.betasys.Model.UserCredentialModel;
import com.noxsoft.betasys.Model.UserModel;
import com.noxsoft.betasys.Service.AuthenticaitonService;
import com.noxsoft.betasys.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Authentication")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthenticationController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticaitonService authenticaitonService;
    @Autowired
    JWTService jwtService;


    @PostMapping("/Register")
    public ResponseModel register(@RequestBody UserModel userModel) {
        return userService.registerUser(userModel);
    }

    @GetMapping(value = "/getuser")
    public UserCredentialModel getUser(){
        return userService.getUserByUsername("nareshdarji4u");
    }

    @PostMapping("/LoginUser")
    public TokenResponse register(@RequestBody UserCredentialModel userCredentialModel) {
        return authenticaitonService.isAuthorized(userCredentialModel);
    }

}
