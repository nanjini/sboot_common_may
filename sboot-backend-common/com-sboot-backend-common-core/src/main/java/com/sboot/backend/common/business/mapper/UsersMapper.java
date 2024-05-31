package com.sboot.backend.common.business.mapper;

import com.sboot.backend.common.business.model.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersMapper {

    int insertUsers(Map<String, Object> map);

    int insertUser(Users user);

    Users getUserByEmail(String email);

    List<Users> getUserAll(Users users);

    int updateUserByEmail(Users users);

    int deleteUserByEmail(Users users);

    int getUserCount(Users users);

}
