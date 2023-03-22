package com.lesson.community.controller;

import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Classname LoginController
 * @Description 跳转登录注册页面
 * @Date 2023/3/22 15:14
 * @Created by hwj
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, UserEntity userEntity){
        Map<String ,Object> map = userService.registerUser(userEntity);
        if(map == null||map.isEmpty()){
            model.addAttribute("msg", "注册成功,我们已将向您的邮箱发送来一封激活邮件,请尽快激活！");
            model.addAttribute("target","/");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }
}
