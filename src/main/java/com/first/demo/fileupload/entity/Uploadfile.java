package com.first.demo.fileupload.entity;

import java.io.Serializable;

public class Uploadfile implements Serializable {


    private String fileName;

    private String operatorName;

    private String uploadTime;

    @Override
    public String toString() {
        return "Uploadfile{" +
                "fileName='" + fileName + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", userId=" + userId +
                '}';
    }

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public Uploadfile() {
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime == null ? null : uploadTime.trim();
    }

}