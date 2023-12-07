package com.noxsoft.betasys.Dao;

import com.noxsoft.betasys.Model.ModuleCreateModel;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ModuleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean insertProjectModule(ModuleCreateModel moduleCreateModel, int userID){

        try{
            String queryForCreateModule = "INSERT INTO betasys_modules(MODULE_NAME,CREATED_BY,PARENT_MODULE,CREATED_ON,PROJECT_ID) values(?,?,?,?,?)";
            int isInsert = jdbcTemplate.update(queryForCreateModule,moduleCreateModel.getModuleName(),userID,(moduleCreateModel.getParentModule()!=-1?moduleCreateModel.getParentModule():0), LocalDateTime.now(),moduleCreateModel.getProjectID());
            if(isInsert==1){
                return true;
            }
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return false;
    }


    public ResponseEntity<List<Map<String,Object>>> selectAllModule(int projectID, String parentModuleID, int userID) {
        String isValidAccess = "SELECT EXISTS (SELECT 1 FROM betasys_project_collaborator WHERE PROJECT_ID = ? AND COLLABORATOR_ID = ?)";
        boolean isExist = jdbcTemplate.queryForObject(isValidAccess, Boolean.class, projectID, userID);
        if (isExist) {
            String queryForGetModules = "SELECT m.MODULE_ID AS moduleID,m.MODULE_NAME AS moduleName,m.PROJECT_ID AS projectID,m.PARENT_MODULE AS parentModule,m.CREATED_ON AS createdOn,m.CREATED_BY AS createdBy,(SELECT COUNT(*) FROM betasys_modules WHERE PARENT_MODULE = m.MODULE_ID) AS subModules FROM betasys_modules m WHERE m.PROJECT_ID = ? AND m.PARENT_MODULE = ?";
            List<Map<String,Object>> moduleList = jdbcTemplate.queryForList(
                    queryForGetModules,
                    projectID, parentModuleID
            );
            return ResponseEntity.of(Optional.of(moduleList));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
