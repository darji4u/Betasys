package com.noxsoft.betasys.Dao;

import com.noxsoft.betasys.Model.CollaboratorModel;
import com.noxsoft.betasys.Model.ProjectModel;
import com.noxsoft.betasys.Model.RowMapper.CollaboratorRowMapper;
import com.noxsoft.betasys.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ProjectDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public Long insertProject(ProjectModel projectModel) {
        try {
            String sqlProject = "INSERT INTO betasys_projects(PROJECT_NAME,DESCRIPTION,CREATED_ON,CREATED_BY) VALUES(?,?,?,?)";
            String sqlProjectID = "SELECT MAX(PROJECT_ID) FROM betasys_projects WHERE CREATED_BY = ?";
            String sqlCollaborator = "INSERT INTO betasys_project_collaborator(COLLABORATOR_ID,PROJECT_ID,DESIGNATION) VALUES(?,?,?)";

            jdbcTemplate.update(sqlProject, projectModel.getProjectName(), projectModel.getDescription(), LocalDateTime.now(), projectModel.getCreatedBy());
            Long proID = jdbcTemplate.queryForObject(sqlProjectID, Long.class, projectModel.getCreatedBy());

            jdbcTemplate.update(sqlCollaborator, projectModel.getCreatedBy(), proID, "PO");
            for (User user : projectModel.getProjectManagers()) {
                jdbcTemplate.update(sqlCollaborator, user.getID(), proID, "PM");
            }
            for (User user : projectModel.getBaTeam()) {
                jdbcTemplate.update(sqlCollaborator, user.getID(), proID, "BA");
            }

            for (User user : projectModel.getDeveloperTeam()) {
                jdbcTemplate.update(sqlCollaborator, user.getID(), proID, "SE");
            }
            for (User user : projectModel.getQaList()) {
                jdbcTemplate.update(sqlCollaborator, user.getID(), proID, "QA");
            }
            return proID;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return -1L;
        }
    }


    public ResponseEntity<List<Map<String, Object>>> selectMyProject(int userID) {
        String queryMyProject = "SELECT Distinct bp.PROJECT_ID as projectID, bp.PROJECT_NAME as projectName, bp.PROJECT_LOGO as projectLogo,bp.CREATED_ON as createdOn, bum.FULL_NAME as managerName, bum.user_profile as managerProfile FROM betasys_projects bp JOIN betasys_project_collaborator bpc ON bp.PROJECT_ID = bpc.PROJECT_ID JOIN betasys_user_master bum ON bp.CREATED_BY = bum.USER_ID WHERE bpc.COLLABORATOR_ID = ?";


        String queryCollaborator = "SELECT DISTINCT bum.full_name as userName, bum.USER_PROFILE as userProfile FROM betasys_user_master bum JOIN betasys_project_collaborator bpc ON bum.USER_ID = bpc.COLLABORATOR_ID WHERE bpc.PROJECT_ID = ?";


        try {
            List<Map<String, Object>> allUserProjects = jdbcTemplate.queryForList(queryMyProject, userID);

            for (int i = 0; i < allUserProjects.size(); i++) {
                List<Map<String, Object>> collaborators = jdbcTemplate.queryForList(queryCollaborator, allUserProjects.get(i).get("projectID"));
                allUserProjects.get(i).put("collaborators", collaborators);
            }
            return ResponseEntity.of(Optional.of(allUserProjects));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Map<String, Object>> getProjectData(int projectID, int userID) {

        String queryforProject = "SELECT bp.PROJECT_ID as projectID, bp.PROJECT_NAME as projectName,bp.DESCRIPTION as description," +
                " bp.PROJECT_LOGO as projectLogo,bp.CREATED_ON as createdOn,bum.user_name as userName, bum.full_name as managerName, bum.user_profile as managerProfile, GROUP_CONCAT(bpc.designation SEPARATOR ', ') as designation FROM betasys_projects bp JOIN betasys_project_collaborator bpc ON bp.PROJECT_ID = bpc.PROJECT_ID JOIN betasys_user_master bum ON bp.CREATED_BY = bum.USER_ID WHERE bpc.COLLABORATOR_ID = ? and bp.PROJECT_ID = ? group by bpc.COLLABORATOR_ID";
        String queryCollaborator = "SELECT DISTINCT bum.full_name as userName, bum.USER_PROFILE as userProfile FROM betasys_user_master bum JOIN betasys_project_collaborator bpc ON bum.user_ID = bpc.COLLABORATOR_ID WHERE bpc.PROJECT_ID = ?";
        try {
            Map<String, Object> project = jdbcTemplate.queryForMap(queryforProject, userID, projectID);
            List<Map<String, Object>> collaborators = jdbcTemplate.queryForList(queryCollaborator, project.get("projectID"));
            project.put("collaborators", collaborators);
            return ResponseEntity.of(Optional.of(project));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Map<String, Object>> getProjectDescription(int projectID, int userID) {

        String queryforDescription = "select distinct bp.description from betasys_projects as bp JOIN betasys_project_collaborator bpc on bp.PROJECT_ID = bpc.PROJECT_ID where bpc.COLLABORATOR_ID = ? and bpc.PROJECT_ID = ?";
        try {
            Map<String, Object> project = jdbcTemplate.queryForMap(queryforDescription, userID, projectID);
            return ResponseEntity.of(Optional.of(project));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    public List<CollaboratorModel> selectProjectCollaborators(int projectID) {
        String queryForCollaborators = "select distinct bum.USER_ID , bum.USER_NAME,bum.FULL_NAME,bum.USER_PROFILE ,GROUP_CONCAT(bpc.DESIGNATION SEPARATOR ',') as DESIGNATION from betasys_user_master as bum JOIN betasys_project_collaborator as bpc on bum.USER_ID = bpc.COLLABORATOR_ID where bpc.PROJECT_ID = ? group by bpc.COLLABORATOR_ID";
        List<CollaboratorModel> collaboratorModelList = jdbcTemplate.query(queryForCollaborators, new CollaboratorRowMapper(), projectID);
        return collaboratorModelList;
    }

    public boolean checkUserMember(int userID, int projectID) {
        String queryToVerifyMember = "SELECT COUNT(*) FROM betasys_project_collaborator WHERE PROJECT_ID = ? AND COLLABORATOR_ID = ?";
        int count = jdbcTemplate.queryForObject(queryToVerifyMember, Integer.class, projectID, userID);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<String> getUserDesignationForPro(int projectID, int userID) {

        String queryForUserDesignation = "SELECT DESIGNATION FROM betasys_project_collaborator WHERE COLLABORATOR_ID = ? AND PROJECT_ID = ?";

        try {
            return jdbcTemplate.queryForList(queryForUserDesignation,String.class, userID, projectID);
        } catch (Exception e) {
            return null;
        }


    }
}
