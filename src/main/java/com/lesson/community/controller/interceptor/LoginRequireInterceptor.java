package com.lesson.community.controller.interceptor;

import com.lesson.community.annotation.LoginRequired;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Classname LoginRequireInterceptor
 * @Description 拦截器, 用于拦截未登录不予访问的请求
 * @Date 2023/3/24 14:35
 * @Created by hwj
 */
@Component
public class LoginRequireInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolderUntil holderUntil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if (loginRequired != null && holderUntil.getUser() == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
