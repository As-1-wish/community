package com.lesson.community.util;

import com.lesson.community.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * @Classname HostHolderUntil
 * @Description 持有用户信息，用于代替session对象，线程隔离
 * @Date 2023/3/24 9:31
 * @Created by hwj
 */
@Component
public class HostHolderUntil {

    private final ThreadLocal<UserEntity> threadLocal = new ThreadLocal<>();

    public void setUser(UserEntity user){
        threadLocal.set(user);
    }

    public UserEntity getUser(){
        return threadLocal.get();
    }

    public void clear(){
        threadLocal.remove();
    }
}
