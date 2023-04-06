package com.lesson.community.controller.advice;

import com.lesson.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @Classname ExceptionAdvice
 * @Description 异常处理
 * @Date 2023/4/6 14:20
 * @Created by hwj
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常:"  + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()){    // 循环输出详细案例
            logger.error(element.toString());
        }

        // 判断异步请求 or 普通请求
        String xRequestdWith = request.getHeader("x-requested-with");
        if(xRequestdWith.equals("XMLHttpRequest")){ // 异步请求
            // /plain 普通类型 但是是json格式
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器异常！"));
        }
        else{
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
