package com.sboot.backend.common.business.service;

import com.sboot.backend.common.business.model.Users;

import java.util.List;
import java.util.Map;

public interface UsersService {

    public int insertUsers(Map<String, Object> map);

    public int insertUser(Users user);

    public Users getUserByEmail(String email);

    public List<Users> getUserAll(Users users);

    public int updateUserByEmail(Users users);

    public int deleteUserByEmail(Users users);

    int getUserCount(Users users);

}
