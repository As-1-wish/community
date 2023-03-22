package com.lesson.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname LoginController
 * @Description 跳转登录注册页面
 * @Date 2023/3/22 15:14
 * @Created by hwj
 */
@Controller
public class LoginController {

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/register";
    }
}
