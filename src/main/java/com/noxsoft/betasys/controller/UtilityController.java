package com.noxsoft.betasys.controller;


import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Utility")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilityController {


    @Autowired
    UtilityService utilityService;

    @RequestMapping(value = "/Designations",method = RequestMethod.GET)
    public List<Map<String,Object>> getDesignation(){
        return utilityService.getDesignation();
    }

    @RequestMapping(value = "/Skills",method = RequestMethod.GET)
    public List<Map<String,Object>> getSkills(){
        return utilityService.getSkills();
    }

    @RequestMapping(value = "/checkUserNameEmail",method = {RequestMethod.GET})
    public ResponseModel checkUserName(@RequestParam("username") String username,@RequestParam("email") String email){
        return utilityService.checkUserName(username,email);
    }


    @RequestMapping(value = "/getTags",method = RequestMethod.GET)
    public List<Map<String,Object>> getTags(){
        return utilityService.getTags();
    }
}
