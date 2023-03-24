package com.lesson.community.service;

import com.lesson.community.entity.LoginTicketEntity;
import com.lesson.community.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserEntity> findAll();


    UserEntity getUserEntityByID(Integer id);

    UserEntity getUserEntityByName(String name);

    int UpdateStatus(Integer id, Integer status);

    int UpdateHeader(Integer id, String headerUrl);

    int Updatepassword(Integer id, String password);

    Map<String, Object> registerUser(UserEntity userEntity);

    int activate(int userId, String code);

    Map<String, Object> loginUser(String username, String password, int expiredSeconds);

    void logoutUser(String ticket);

    LoginTicketEntity getUserEntityByTicket(String ticket);


}
