package com.lesson.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname LoginController
 * @Description 登录页面操作
 * @Date 2023/3/22 22:01
 * @Created by hwj
 */
@Controller
public class LoginController {

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }
}
