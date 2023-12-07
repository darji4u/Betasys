package com.noxsoft.betasys.controller;

import com.noxsoft.betasys.Model.ModuleCreateModel;
import com.noxsoft.betasys.Model.ResponseModel;
import com.noxsoft.betasys.Service.ModuleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Modules")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ModuleController{

    @Autowired
    ModuleService moduleService;

    ResponseModel responseModel;

    @RequestMapping(value ="/createProjectModule",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> postModule(@RequestBody ModuleCreateModel moduleCreateModule, HttpServletRequest request){
        try{
            String token = request.getHeader("Authorization");
            responseModel = moduleService.createModule(token,moduleCreateModule);
            return ResponseEntity.of(Optional.of(responseModel));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @RequestMapping(value = "/getAllModules",method = RequestMethod.GET)
    public ResponseEntity<List<Map<String,Object>>> getAllModules(@RequestParam("projectID")int projectID, @RequestParam("parentModuleID") String parentModuleID, HttpServletRequest request){
        try{
            String token = request.getHeader("Authorization");
            return moduleService.getAllModules(projectID,parentModuleID,token);
        }catch (Exception e){
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
