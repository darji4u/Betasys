package com.noxsoft.betasys.Model.RowMapper;

import com.noxsoft.betasys.Model.CollaboratorModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CollaboratorRowMapper implements RowMapper<CollaboratorModel> {
    @Override
    public CollaboratorModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        CollaboratorModel collaboratorModel = new CollaboratorModel();
        collaboratorModel.setUserID(rs.getInt("USER_ID"));
        collaboratorModel.setUserName(rs.getString("USER_NAME"));
        collaboratorModel.setUserFullName(rs.getString("FULL_NAME"));
        collaboratorModel.setUserProfile(rs.getBytes("USER_PROFILE"));

        String designationsString = rs.getString("DESIGNATION");
        List<String> designations = Arrays.asList(designationsString.split(","));
        collaboratorModel.setDesignations(designations);

        return collaboratorModel;
    }
}
