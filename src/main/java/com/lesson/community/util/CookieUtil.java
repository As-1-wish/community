package com.lesson.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Classname CookieUtil
 * @Description cookie 工具类
 * @Date 2023/3/24 9:10
 * @Created by hwj
 */
public class CookieUtil {

    /**
     * @author hwj
     * @Description 返回 cookie 中的 name 值
     * @date 2023/3/24 9:13
     */
    public static String getValue(HttpServletRequest request, String name){
        if(request == null || name == null)
            throw new IllegalArgumentException("参数为空！");
        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
            for(Cookie cookie:cookies)
                if(cookie.getName().equals(name))
                    return cookie.getValue();
        return null;
    }
}
