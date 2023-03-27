package com.lesson.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
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
        if(StringUtils.isBlank(key))
            return null;
        return DigestUtils.md5DigestAsHex(key.getBytes())   ;
    }

    public static String getJSONString(int code,String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg",msg);
        if(map!=null){
            for(String key : map.keySet()){
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }
    public static String getJSONString(int code,String msg){
        return getJSONString(code, msg, null);
    }
    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }
}
