package com.lesson.community.service.impl;

import com.lesson.community.dao.LoginTicketRepository;
import com.lesson.community.dao.UserRepository;
import com.lesson.community.entity.LoginTicketEntity;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.ConstantUtil;
import com.lesson.community.util.MailClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, ConstantUtil {

    @Autowired
    private UserRepository userRepository;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailClientUtil mailClient;

    @Autowired
    private LoginTicketRepository loginTicketRepository;

    /**
     * @param userEntity 用户实体
     * @author hwj
     * @Description 用户注册业务
     * @date 2023/3/22 21:47
     */
    @Override
    public Map<String, Object> registerUser(UserEntity userEntity) {
        Map<String, Object> map = new HashMap<>();
        // 字段是否为空前台已经处理，保证传过来的值不会为空
        //验证待注册账号
        if (userRepository.getUserEntityByName(userEntity.getUsername()) != null) {
            map.put("usernameMsg", "该账号已存在！");
            return map;
        }
        if (userRepository.getUserEntityByEmail(userEntity.getEmail()) != null) {
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

        userRepository.saveAndFlush(userEntity);

        // 发送激活邮件
        Context context = new Context();
        context.setVariable("email", userEntity.getEmail());
        // 激活链接:https://localhost:8081/community/activation/101/code
        String url = domain + contextPath + "/activation/" + userEntity.getId()
                + "/" + userEntity.getActivationCode();
        System.out.printf("激活链接为:%s",url);
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.senMail(userEntity.getEmail(), "牛客账号激活", content);

        return map;
    }

    /**
     * @param userId 待激活用户ID
     * @param code   待激活用户所提供激活码
     * @author hwj
     * @Description 进行用户账户激活
     * @date 2023/3/22 21:50
     */
    @Override
    public int activate(int userId, String code) {
        System.out.printf(String.format("userService层:userID=%d, code=%s", userId, code));
        UserEntity user = userRepository.getUserEntityByID(userId);
        if (user.getStatus() == 1)
            return ACTIVATION_REPEAT;
        else if (user.getActivationCode().equals(code)) {
            userRepository.UpdateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else
            return ACTIVATION_FAILURE;
    }

    /**
     * @author hwj
     * @Description 用户登录业务
     * @date 2023/3/23 15:01
     */
    public Map<String, Object> loginUser(String username, String password, int expiredSeconds){
        Map<String, Object> map = new HashMap<>();
        //验证账号
        UserEntity user = userRepository.getUserEntityByName(username);
        if(user==null){ //账号是否存在
            map.put("usernameMsg", "该账号不存在！");
            return map;
        }
        if(user.getStatus()==0){ //账号是否激活
            map.put("statusMsg", "该账号未激活！");
            return map;
        }
        if(!user.getPassword().equals(CommunityUtil.Encrypt(password + user.getSalt()))){ // 密码是否正确
            map.put("passwordMsg", "密码错误！");
            return map;
        }

        //生成登录凭证
        LoginTicketEntity loginTicket = new LoginTicketEntity();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.getUUID());
        loginTicket.setExpired(new Timestamp(new Date().getTime() + expiredSeconds * 1000L));
        loginTicketRepository.saveAndFlush(loginTicket);
        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserEntityByID(Integer id) {
        return userRepository.getUserEntityByID(id);
    }

    @Override
    public UserEntity getUserEntityByName(String name) {
        return userRepository.getUserEntityByName(name);
    }


    @Override
    public int UpdateStatus(Integer id, Integer status) {
        return userRepository.UpdateStatus(id, status);
    }

    @Override
    public int UpdateHeader(Integer id, String headerUrl) {
        return userRepository.UpdateHeader(id, headerUrl);
    }

    @Override
    public int Updatepassword(Integer id, String password) {
        return userRepository.UpdatePassword(id, password);
    }

}
