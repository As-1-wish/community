package com.lesson.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Classname CommunityUtil
 * @Description 一些基本的方法
 * @Date 2023/3/22 15:44
 * @Created by hwj
 */
public class CommunityUtil {


    // 生成随机字符串
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密
    public static String Encrypt(String key){
        if(StringUtils.isEmpty(key))
            return null;
        return DigestUtils.md5DigestAsHex(key.getBytes())   ;
    }
}
