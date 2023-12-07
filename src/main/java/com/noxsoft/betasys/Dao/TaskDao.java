package com.noxsoft.betasys.Dao;

import com.noxsoft.betasys.Model.*;
import com.noxsoft.betasys.Utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TaskDao {

    Utility utility;


    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long  insertTask(TaskCreateModule taskCreateModule) {

        String queryForTask = "INSERT INTO betasys_task_master(" +
                "TASK_NAME," +
                "TASK_DESCRIPTION," +
                "TAGS," +
                "PRIORITY," +
                "MODULE_ID," +
                "PROJECT_ID," +
                "CREATED_BY," +
                "CREATED_ON," +
                "DEADLINE," +
                "PARENT_TASK_ID," +
                "REMARK)" +
                " values(?,?,?,?,?,?,?,?,?,?,?)";

        String queryTaskID = "SELECT MAX(TASK_ID) FROM betasys_task_master WHERE CREATED_BY = ?";
        String queryForAssignee = "INSERT INTO betasys_task_assignee(" +
                "TASK_ID," +
                "ASSIGNEE_ID," +
                "SEEN," +
                "TASK_STATUS) VALUES(?,?,?,?)";

        jdbcTemplate.update(
                queryForTask,
                taskCreateModule.getTaskName(),
                taskCreateModule.getTaskDescription(),
                taskCreateModule.getTags(),
                (taskCreateModule.getPriority().equalsIgnoreCase("High")?1:(taskCreateModule.getPriority().equalsIgnoreCase("Medium")?2:1)),
                taskCreateModule.getModuleID(),
                taskCreateModule.getProjectID(),
                taskCreateModule.getCreatedBy(),
                LocalDateTime.now(),
                taskCreateModule.getDedline(),
                taskCreateModule.getParentTaskID(),
                ""
                );

        Long taskID = jdbcTemplate.queryForObject(queryTaskID, Long.class, taskCreateModule.getCreatedBy());


        for(Assignee assignee:taskCreateModule.getAssignee()){
            jdbcTemplate.update(queryForAssignee,taskID,assignee.getUserID(),0,"TD");
        }

        return taskID;
    }

    public List<Map<String,Object>> getTasks(int projectID, int moduleID, int userID) {

        String queryForTask = "SELECT " +
                                        "BTM.TASK_ID as taskID," +
                                        "BTM.TASK_NAME as taskName," +
                                        "DATE(BTM.DEADLINE) as deadline," +
                                        "BTA.SEEN as seen," +
                                        "BTA.TASK_STATUS as taskStatus," +
                                        "BTM.TAGS as taskType," +
                                        "BM.MODULE_ID as moduleID," +
                                        "BM.MODULE_NAME as moduleName," +
                                        "BTM.PARENT_TASK_ID AS parentTaskID " +
                                        "FROM betasys_task_assignee AS BTA " +
                                        "JOIN betasys_task_master AS BTM ON BTA.TASK_ID = BTM.TASK_ID " +
                                        "JOIN betasys_modules AS BM ON BTM.MODULE_ID  = BM.MODULE_ID " +
                                        "WHERE BTA.ASSIGNEE_ID = ? AND BTM.PROJECT_ID = ? " +
                                        "order by BTM.CREATED_ON desc";

        if(moduleID==0){
            return jdbcTemplate.queryForList(queryForTask,userID,projectID);
        }else{
            return jdbcTemplate.queryForList(queryForTask,projectID,moduleID,userID);
        }
    }

    public CustomResponse<Map<String, Object>> selectTaskDetail(int projectID, int moduleID, int taskID) {
        Map<String,Object> taskData = new HashMap<>();
        try{
            String queryForTaskDetail = "SELECT " +
                    "btm.TASK_ID as taskID," +
                    "btm.TASK_NAME as taskName, " +
                    "btm.TASK_DESCRIPTION as taskDescription, " +
                    "btm.TAGS as tag, " +
                    "btm.PRIORITY as priority, " +
                    "btm.CREATED_ON as createdOn, " +
                    "btm.DEADLINE as deadline, " +
                    "bum.USER_PROFILE as creatorProfile, " +
                    "bum.FULL_NAME as creatorName, " +
                    "btm.CREATED_BY as creatorID " +
                    "FROM betasys_task_master as btm " +
                    "INNER JOIN betasys_user_master as bum " +
                    "on btm.CREATED_BY = bum.USER_ID " +
                    "where btm.PROJECT_ID = ? "+
                    "AND btm.MODULE_ID = ? " +
                    "AND btm.TASK_ID = ?";

            taskData = jdbcTemplate.queryForMap(queryForTaskDetail,projectID,moduleID,taskID);


            List<Map<String,Object>> assigneeResponseData = selectForTaskAssigneeDetail(taskID);

            List<Map<String,Object>> assigneeData = new ArrayList<>();

            for(Map<String,Object> assignee : assigneeResponseData){

                    String[] designation = String.valueOf(assignee.get("designation")).split(",");

                    for(String des : designation){
                        switch (des){
                            case "SE":
                                assignee.put("level",5);
                                break;
                            case "QA":
                                assignee.put("level",4);
                                assignee.put("designation","QA");
                                break;
                            case "BA":
                                assignee.put("level",3);
                                assignee.put("designation","BA");
                                break;
                            case "PM":
                                assignee.put("level",2);
                                assignee.put("designation","PM");
                                break;
                            case "PO":
                                assignee.put("level",1);
                                assignee.put("designation","PO");
                                break;
                        }

                        Map<String,Object> assigneeObj = new HashMap<>();
                        assigneeObj.putAll(assignee);
                        assigneeData.add(assigneeObj);
                    }



                }

            assigneeData.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    Integer level1 = (Integer) map1.get("level");
                    Integer level2 = (Integer) map2.get("level");
                    return level2 - level1;
                }
            });
            taskData.put("assigneeData",assigneeData);

            String queryForFiles = "SELECT FILE as file, FILE_NAME as fileName, CREATED_ON as createdON,FILE_TYPE as fileType FROM betasys_documents WHERE BELONGS_TO_ID = ? AND BELONGS_TO = \"TASK\"";

            List<Map<String,Object>> files = jdbcTemplate.queryForList(queryForFiles,taskID);

            taskData.put("files",files);




        }catch (Exception e){
            CustomResponse<Map<String,Object>> taskResponse = new CustomResponse<>();
            taskResponse.setMessage("No Data Found");
            taskResponse.setStatus("Failed");
            taskResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return taskResponse;
        }


        CustomResponse<Map<String,Object>> taskResponse = new CustomResponse<>();
        taskResponse.setStatus("Success");
        taskResponse.setData(taskData);
        taskResponse.setStatusCode(200);
        return taskResponse;

    }

    public List<Map<String,Object>> selectForTaskAssigneeDetail(int taskID){
        String queryForTaskAssigneeDetail = "" +
                "SELECT " +
                "    DISTINCT\n" +
                "    bum.USER_ID AS assigneeID,\n" +
                "    bum.FULL_NAME AS assigneeName,\n" +
                "    bum.USER_PROFILE AS assigneeProfile,\n" +
                "    bta.TASK_STATUS AS taskStatus,\n" +
                "    GROUP_CONCAT(DISTINCT bpc.DESIGNATION SEPARATOR \",\") AS designation\n" +
                "FROM\n" +
                "    betasys_task_assignee bta \n" +
                "INNER JOIN\n" +
                "    betasys_user_master bum ON bta.ASSIGNEE_ID = bum.USER_ID \n" +
                "JOIN\n" +
                "    betasys_project_collaborator AS bpc ON bta.ASSIGNEE_ID = bpc.COLLABORATOR_ID\n" +
                "WHERE\n" +
                "    bta.TASK_ID = ?\n" +
                "group by\n" +
                "\tbum.USER_ID ,\n" +
                "    bum.FULL_NAME ,\n" +
                "    bum.USER_PROFILE ,\n" +
                "    bta.TASK_STATUS;";

        List<Map<String,Object>> assigneeResponseData = jdbcTemplate.queryForList(queryForTaskAssigneeDetail,taskID);
        return assigneeResponseData;
    }


    public List<Map<String, Object>> selectTasksForAction(int projectID, int taskID, int userID) {

        String query = "SELECT\n" +
                "    btm.TASK_ID AS taskID,\n" +
                "    btm.TASK_NAME AS taskName,\n" +
                "    btm.TAGS AS tag,\n" +
                "    btm.CREATED_ON AS createdON,\n" +
                "    bum.USER_ID AS assignorID,\n" +
                "    bum.FULL_NAME AS assignorName,\n" +
                "    bum.USER_PROFILE AS assignorProfile,\n" +
                "    btm.REMARK AS remark,\n" +
                "    bta.TASK_STATUS AS taskStatus,\n" +
                "    GROUP_CONCAT(DISTINCT bpc.DESIGNATION SEPARATOR \",\") AS designation\n" +
                "FROM\n" +
                "    betasys_task_master AS btm\n" +
                "JOIN\n" +
                "    betasys_user_master AS bum ON btm.CREATED_BY = bum.USER_ID\n" +
                "JOIN\n" +
                "    betasys_project_collaborator AS bpc ON btm.CREATED_BY = bpc.COLLABORATOR_ID\n" +
                "JOIN\n" +
                "    betasys_task_assignee AS bta \n" +
                "WHERE\n" +
                "    bpc.PROJECT_ID = ?\n" +
                "    AND bta.ASSIGNEE_ID = ?\n" +
                "    AND (btm.TASK_ID = ? OR btm.PARENT_TASK_ID = ?)" +
                "GROUP BY\n" +
                "    btm.TASK_ID,\n" +
                "    btm.TASK_NAME,\n" +
                "    btm.TAGS,\n" +
                "    btm.CREATED_ON,\n" +
                "    bum.USER_ID,\n" +
                "    bum.FULL_NAME,\n" +
                "    bum.USER_PROFILE,\n" +
                "    bta.TASK_STATUS;";

        List<Map<String,Object>> taskActionListForInProgress = jdbcTemplate.queryForList(query,projectID,userID,taskID,taskID);
        return taskActionListForInProgress;

    }

    public boolean isTaskPendingAboveUser(int taskID, String preDesignation) {

        String query = "SELECT " +
                "count(*) " +
                "FROM betasys_project_collaborator as bpc\n" +
                "JOIN betasys_task_assignee as bta \n" +
                "ON bpc.COLLABORATOR_ID = bta.ASSIGNEE_ID\n" +
                "where bpc.DESIGNATION LIKE \"%?%\" AND bta.TASK_ID = ? AND bta.TASK_STATUS = DN";

        int isDone = jdbcTemplate.queryForObject(query,Integer.class,preDesignation,taskID);

        return isDone==1?true:false;

    }

    public void updateTaskAction(int userID, TaskActionRequestModel taskActionRequestModel) {

        switch (taskActionRequestModel.getAction()){
            case "IP":


                    String queryForTaskEffort = "INSERT INTO betasys_work_efforts(WORK_ID,USER_ID,WORK_TYPE,STATUS,START_TIME,REMARKS) VALUES(?,?,?,?,?,?)";

                    jdbcTemplate.update(queryForTaskEffort,taskActionRequestModel.getTaskID(),userID,"TASK","IP",LocalDateTime.now(),taskActionRequestModel.getRemarks());

                    String queryForUpdateTaskStatus = "UPDATE betasys_task_assignee SET TASK_STATUS = 'IP' where TASK_ID = ? AND ASSIGNEE_ID = ?";

                    jdbcTemplate.update(queryForUpdateTaskStatus,taskActionRequestModel.getTaskID(),userID);

                break;
            case "POS":
                break;
            case "DN":
                break;
            case "SEEN":
                String queryForUpdateTaskSeen = "UPDATE betasys_task_assignee SET SEEN = 1 WHERE TASK_ID = ? AND ASSIGNEE_ID = ?";
                jdbcTemplate.update(queryForUpdateTaskSeen,taskActionRequestModel.getTaskID(),userID);
                break;
        }

    }
}
