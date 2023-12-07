package com.noxsoft.betasys.Utility;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import ch.qos.logback.core.util.FileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;

public class Utility {

    public String getExtention(String fileName){
        String[] extention = fileName.split(".");
        return extention[extention.length-1];
    }

    public String convertBlobToString(Object taskDescription) {

        Blob taskDescriptionBlob = (Blob) taskDescription;
        if (taskDescriptionBlob != null) {
            try (InputStream inputStream = taskDescriptionBlob.getBinaryStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return "";
    }
}
