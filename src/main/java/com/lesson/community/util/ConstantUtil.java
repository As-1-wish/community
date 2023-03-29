package com.lesson.community.util;

/**
 * @author hwj
 * @Description 定义常量
 * @date 2023/3/22 21:44
 */
public interface ConstantUtil {

    // 激活成功
    int ACTIVATION_SUCCESS = 0;

    // 激活成功
    int ACTIVATION_REPEAT = 1;

    // 激活成功
    int ACTIVATION_FAILURE = 2;

    // 默认转态的登录凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // 记住状态的登录凭证的超时时间
    int REMENBER_EXPIRED_SECONDS = 3600 * 24 * 15;

    //实体类型：帖子
    int ENTITY_TYPE_POST = 1;

    //实体类型：评论
    int ENTITY_TYPE_COMMENT = 2;

}
