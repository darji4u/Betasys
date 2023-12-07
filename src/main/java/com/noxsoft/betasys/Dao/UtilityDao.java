package com.noxsoft.betasys.Dao;

import com.noxsoft.betasys.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class UtilityDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> getDesignation() {
        String sql = "select * from betasys_DESIGNATIONS";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> getSkills(){
        String sql = "select * from betasys_ITSKILLS";
        return jdbcTemplate.queryForList(sql);
    }

    public int checkUserName(String username) {
        String query = "SELECT COUNT(*) FROM betasys_user_master WHERE USER_NAME = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, username);
        return count;
    }

    public int checkEmail(String email) {
        String query = "SELECT COUNT(*) FROM betasys_user_master WHERE EMAIL = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, email);
        return count;
    }

    public boolean insertDocuments(MultipartFile[] files, Long id,String belongsTO) {
        String query = "INSERT INTO betasys_documents(BELONGS_TO_ID,BELONGS_TO,FILE,FILE_NAME,FILE_TYPE,CREATED_ON) VALUES(?,?,?,?,?,?)";
        try{
            for (MultipartFile file: files){
                jdbcTemplate.update(query,id,belongsTO,file.getBytes(),file.getOriginalFilename(),file.getContentType(), LocalDateTime.now());
            }
            return true;
        }catch (Exception e){
            System.out.print(e.getMessage());
            return false;
        }
    }

    public List<Map<String, Object>> selectTags() {

        String queryForTags = "SELECT TAG_ID as tagID,TAG_NAME as tagName from betasys_tags";
        return jdbcTemplate.queryForList(queryForTags);
    }
}
