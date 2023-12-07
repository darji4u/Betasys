package com.noxsoft.betasys.Dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noxsoft.betasys.Model.UserCredentialModel;
import com.noxsoft.betasys.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public int createUser(UserModel userModel) {
        String sql = "INSERT INTO betasys_user_master(USER_NAME,FULL_NAME,EMAIL,GENDER,DESIGNATION,USER_PASSWORD,IS_ENABLE,USER_STATUS,SKILLS)" +
                " VALUES(?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,userModel.getUserName(),userModel.getFullName(),userModel.getEmail(),userModel.getGender(),
                                    userModel.getDesignation(),userModel.getPassword(),"UnVerified","Offline",userModel.getSkills()
                                    );
    }

    public UserCredentialModel getUserByUsername(String username) {
        String sql = "select user_name as userName, user_password as password from betasys_user_master where user_name = ?";
        UserCredentialModel userCredential;
        try {
            userCredential = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserCredentialModel.class), username);
        } catch (Exception e) {
            return null;
        }
        return userCredential;
    }

    public List<Map<String,Object>> findUserByUserNameLike(String username) {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "select user_id as ID, user_name as userName, user_profile as userProfile from betasys_user_master where user_name LIKE ? ORDER BY user_name ASC";
        username = username + "%";
        try {
            list = jdbcTemplate.queryForList(sql, username);
            return list;
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return list;
    }

    public UserModel findUserByUserName(String username) {
        Map<String,Object> userData = new HashMap<>();
        UserModel userModel;
        String sql = "select * from betasys_user_master where user_name = ?";
        try {
             userData = jdbcTemplate.queryForMap(sql,username);
             userModel = objectMapper.convertValue(userData,UserModel.class);
             return userModel;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }

    }

    public List<String> getUserDesignations(int projectID, int userID) {
        String queryForUserDesignations = "SELECT DESIGNATION as designation FROM betasys_project_collaborator WHERE PROJECT_ID = ? AND COLLABORATOR_ID = ?";
        return jdbcTemplate.queryForList(queryForUserDesignations,String.class,projectID,userID);
    }
}
