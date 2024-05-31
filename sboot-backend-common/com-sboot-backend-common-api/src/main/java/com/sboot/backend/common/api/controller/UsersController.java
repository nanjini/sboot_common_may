package com.sboot.backend.common.api.controller;

import com.sboot.backend.common.business.model.Users;
import com.sboot.backend.common.business.model.UsersReq;
import com.sboot.backend.common.business.service.UsersService;
import com.sboot.backend.common.core.api.RestApiResponse;
import com.sboot.backend.common.core.exception.UserNotFoundException;
import com.sboot.backend.common.core.utils.DateTimeUtil;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    private final EmailValidator validator = EmailValidator.getInstance();

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestBody Users users){

        List<Users> listUsers = usersService.getUserAll(users);

        if(listUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        int userCnt = usersService.getUserCount(users);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(listUsers)
                .resultCode(HttpStatus.OK.value())
                .resultUserMessage("요청하신 User 정보가 " + userCnt + "건 조회 되었습니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email){

        Users users = usersService.getUserByEmail(email);

        if(users == null) {
            return ResponseEntity.noContent().build();
        }

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(users)
                .resultCode(HttpStatus.OK.value())
                .resultUserMessage("요청하신 User 정보가 정상적으로 조회되었습니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestParam String email){

        Users users = usersService.getUserByEmail(email);


        if(users == null) {
            return ResponseEntity.noContent().build();
        }

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(users)
                .resultCode(HttpStatus.OK.value())
                .resultUserMessage("요청하신 User 정보가 정상적으로 조회되었습니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/saveOne")
    public ResponseEntity<Object> insertUser(@RequestBody Users user){

        RestApiResponse<Object> response = null;
        user.setRegisterDt(DateTimeUtil.nowString());

        try {
            if (GenericValidator.isBlankOrNull(user.getName())) {
                throw new IllegalArgumentException("Invalid name");
            }
            if (!validator.isValid(user.getEmail())) {
                throw new IllegalArgumentException("Invalid e-mail");
            }

            usersService.insertUser(user);
            response = RestApiResponse.builder()
                    .result(user)
                    .resultCode(HttpStatus.OK.value())
                    .resultUserMessage("요청하신 User 정보가 정상적으로 처리되었습니다.")
                    .build();

        } catch (Exception ex) {
            response = RestApiResponse.builder()
                    .result(user)
                    .resultCode(HttpStatus.BAD_REQUEST.value())
                    .resultUserMessage(ex.toString())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> insertUsers(@RequestBody UsersReq userList){

        RestApiResponse<Object> response = null;
        Map<String, Object> map = new HashMap<String, Object>();

        String now = DateTimeUtil.nowString();

        userList.getUserList().forEach(item -> {item.setRegisterDt(now);});
        map.put("userList", userList.getUserList());
        usersService.insertUsers(map);

        response = RestApiResponse.builder()
                .result(map)
                .resultCode(HttpStatus.OK.value())
                .resultUserMessage("요청하신 User 정보가 정상적으로 처리되었습니다.")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Object> updateUserByEmail(@RequestBody Users users, @PathVariable String email){

        RestApiResponse<Object> response = null;

        try{
            Users existUser = usersService.getUserByEmail(email);

            if(existUser == null) {
                throw new UserNotFoundException("User not found");
            }

            usersService.updateUserByEmail(users);
            Users resUser = usersService.getUserByEmail(email);

            response = RestApiResponse.builder()
                    .result(resUser)
                    .resultCode(HttpStatus.OK.value())
                    .resultUserMessage("요청하신 User 정보가 정상적으로 처리되었습니다.")
                    .build();
        }catch(Exception ex) {
            response = RestApiResponse.builder()
                    .result(users)
                    .resultCode(HttpStatus.BAD_REQUEST.value())
                    .resultUserMessage(ex.toString())
                    .build();

            return ResponseEntity.badRequest().body(response);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/delete/{email}")
    public ResponseEntity<Object> deleteUserByEmail(@PathVariable String email, @RequestBody Users users){

        RestApiResponse<Object> response = null;

        Users existUser = usersService.getUserByEmail(email);

        if(existUser == null) {
            return ResponseEntity.noContent().build();
        }

        String now = DateTimeUtil.nowString();

        users.setDeleteDt(now);
        usersService.deleteUserByEmail(users);

        response = RestApiResponse.builder()
                .result(email)
                .resultCode(HttpStatus.OK.value())
                .resultUserMessage("요청하신 User 정보가 정상적으로 삭제 처리되었습니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
