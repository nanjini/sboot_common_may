package com.sboot.backend.common.business.model;

import java.util.List;

public class UsersReq {

    private List<Users> userList;

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }
}
