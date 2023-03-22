package com.lesson.community.service;

import com.lesson.community.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<UserEntity> findAll();

    public void saveAndFlush(UserEntity userEntity);

    UserEntity getUserEntityByID(Integer id);

    UserEntity getUserEntityByName(String name);

    UserEntity getUserEntityByEmail(String email);

    public int UpdateStatus(Integer id, Integer status);

    public int UpdateHeader(Integer id, String headerUrl);

    public int Updatepassword(Integer id, String password);

    public Map<String, Object> registerUser(UserEntity userEntity);
}
