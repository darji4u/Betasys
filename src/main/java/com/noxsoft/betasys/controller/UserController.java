package com.noxsoft.betasys.controller;

import com.noxsoft.betasys.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Users")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/findUserByUsername",method = RequestMethod.GET)
    public List<Map<String,Object>> findUserByUserNameLike(@RequestParam("username") String username){
        return userService.findUserByUserNameLike(username);
    }

}
