package com.lesson.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname ServiceLogAspect
 * @Description 业务统一记录日志
 * @Date 2023/4/6 15:31
 * @Created by hwj
 */
@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    // @Pointcut("execution(方法返回类型 项目位置.包名.符合条件类名.符合条件方法名(符合条件参数))")
    @Pointcut("execution(* com.lesson.community.service.*.*(..))")
    public void pointcut() {
    }

    // 方法执行前调用(参数是目标对象)
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 用户 [IP地址] 在 [时间] 访问了 [何类何方法]
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();       // 获取 Request对象
        String ip = request.getRemoteHost();        // 获取ip
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取所执行的目标对象名字
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        logger.info(String.format("用户 [%s] 在 [%s] 访问了 [%s]", ip, now, target));
    }
}
