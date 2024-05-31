package com.sboot.backend.common.business.service;

import com.sboot.backend.common.business.mapper.UsersMapper;
import com.sboot.backend.common.business.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService{

    private final UsersMapper usersMapper;

    public UsersServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public int insertUsers(Map<String, Object> map) {
        return usersMapper.insertUsers(map);
    }

    @Override
    public int insertUser(Users users) {
        return usersMapper.insertUser(users);
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersMapper.getUserByEmail(email);
    }

    @Override
    public List<Users> getUserAll(Users users) {
        return usersMapper.getUserAll(users);
    }

    @Override
    public int updateUserByEmail(Users users) {
        return usersMapper.updateUserByEmail(users);
    }

    @Override
    public int deleteUserByEmail(Users users) {
        return usersMapper.deleteUserByEmail(users);
    }

    @Override
    public int getUserCount(Users users) {
        return usersMapper.getUserCount(users);
    }


}
