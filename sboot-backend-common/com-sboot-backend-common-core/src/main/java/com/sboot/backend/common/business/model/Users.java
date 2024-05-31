package com.sboot.backend.common.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class Users {

    private int id;

    private String email;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String registerDt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deleteDt;

    private String useFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterDt() {
        return registerDt;
    }

    public void setRegisterDt(String registerDt) {
        this.registerDt = registerDt;
    }

    public String getDeleteDt() {
        return deleteDt;
    }

    public void setDeleteDt(String deleteDt) {
        this.deleteDt = deleteDt;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }
}
