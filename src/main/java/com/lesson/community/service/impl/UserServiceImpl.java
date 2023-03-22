package com.lesson.community.service.impl;

import com.lesson.community.dao.UserDao;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao ;
    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    @Override
    public void saveAndFlush(UserEntity userEntity) {
        userDao.saveAndFlush(userEntity);
    }

    @Override
    public UserEntity getUserEntityByID(Integer id) {
        return userDao.getUserEntityByID(id);
    }

    @Override
    public UserEntity getUserEntityByName(String name) {
        return userDao.getUserEntityByName(name);
    }

    @Override
    public UserEntity getUserEntityByEmail(String email) {
        return userDao.getUserEntityByEmail(email);
    }

    @Override
    public int UpdateStatus(Integer id, Integer status) {
        return userDao.UpdateStatus(id, status);
    }

    @Override
    public int UpdateHeader(Integer id, String headerUrl) {
        return userDao.UpdateHeader(id, headerUrl);
    }

    @Override
    public int Updatepassword(Integer id, String password) {
        return userDao.UpdatePassword(id, password);
    }
}
