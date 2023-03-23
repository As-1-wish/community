package com.lesson.community.controller;

import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.UserService;
import com.lesson.community.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Classname LoginController
 * @Description 注册页面操作
 * @Date 2023/3/22 15:14
 * @Created by hwj
 */
@Controller
public class RegisterController implements ConstantUtil {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    /**
     * @author hwj
     * @Description 接受前台数据,调用后台方法判断是否可以注册,并发送激活邮件
     * @date 2023/3/22 21:52
     * @param userEntity 用户实体
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, UserEntity userEntity){
        Map<String ,Object> map = userService.registerUser(userEntity);
        if(map == null||map.isEmpty()){
            model.addAttribute("msg", "注册成功,我们已将向您的邮箱发送来一封激活邮件,请尽快激活！");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            model.addAttribute("user", userEntity);
            return "/site/register";
        }
    }

    /**
     * @author hwj
     * @Description 通过后台处理激活结果跳转不同页面
     * @date 2023/3/22 22:00
     */
    // 激活链接:https://localhost:8081/community/activation/101/code
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activate(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code){
        System.out.println(userId);
        System.out.println(code);
        int activation = userService.activate(userId, code);

        if(activation==ACTIVATION_SUCCESS){
            model.addAttribute("msg", "激活成功,您的账户可以正常使用了！");
            model.addAttribute("target","/login");
        }else if(activation==ACTIVATION_FAILURE){
            model.addAttribute("msg", "激活失败,请重试！");
            model.addAttribute("target","/index");
        }else{
            model.addAttribute("msg", "无效操作,该账户已被激活！");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }
}
