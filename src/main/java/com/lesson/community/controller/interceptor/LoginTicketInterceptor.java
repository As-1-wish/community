package com.lesson.community.controller.interceptor;

import com.lesson.community.entity.LoginTicketEntity;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CookieUtil;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Classname LoginTicketInterceptor
 * @Description 登录凭证相关拦截器，用来显示用户信息
 * @Date 2023/3/24 9:15
 * @Created by hwj
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolderUntil holderUntil;

    /**
     * @author hwj
     * @Description 在 Controller 处理之前进行调用,用于存入用户信息
     * @date 2023/3/24 9:50
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // 从 cookie 中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            LoginTicketEntity loginTicket = userService.getUserEntityByTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据凭证查询用户
                UserEntity user = userService.getUserEntityByID(loginTicket.getUserId());
                // 在本次请求中持有用户
                holderUntil.setUser(user);
            }
        }
        return true;
    }

    /**
     * @author hwj
     * @Description Controller 处理后、模板渲染前执行,将用户信息存于前端
     * @date 2023/3/24 9:52
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        UserEntity user = holderUntil.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    /**
     * @author hwj
     * @Description 请求结束,清理数据
     * @date 2023/3/24 9:55
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        holderUntil.clear();
    }
}
