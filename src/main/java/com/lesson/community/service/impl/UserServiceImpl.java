package com.lesson.community.service.impl;

import com.lesson.community.dao.UserDao;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.MailClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClientUtil mailClient;

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

    @Override
    public Map<String, Object> registerUser(UserEntity userEntity) {
        Map<String, Object> map = new HashMap<>();
        // 字段是否为空前台已经处理，保证传过来的值不会为空
        //验证待注册账号
        if (userDao.getUserEntityByName(userEntity.getUsername()) != null) {
            map.put("usernameMsg", "该账号已存在！");
            return map;
        }
        if (userDao.getUserEntityByEmail(userEntity.getEmail()) != null) {
            map.put("emailMsg", "该邮箱已被注册！");
            return map;
        }

        //验证无误进行注册,需补充其他字段
        userEntity.setSalt(CommunityUtil.getUUID().substring(0, 5));
        userEntity.setPassword(CommunityUtil.Encrypt(userEntity.getPassword() +
                userEntity.getSalt()));
        userEntity.setActivationCode(CommunityUtil.getUUID());
        userEntity.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        userEntity.setCreateTime(new Timestamp(new Date().getTime()));

        userDao.saveAndFlush(userEntity);

        // 发送激活邮件
        Context context = new Context();
        context.setVariable("email", userEntity.getEmail());
        // 激活链接:https://localhost:8081/community/activation/101/code
        String url = domain + contextPath + "/activation/" + userEntity.getId()
                    + "/" + userEntity.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.senMail(userEntity.getEmail(), "牛客账号激活", content);

        return map;
    }
}
