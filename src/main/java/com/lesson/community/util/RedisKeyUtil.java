package com.lesson.community.util;

/**
 * @Classname RedisKeyUtil
 * @Description Redis key 的工具类
 * @Date 2023/4/7 9:43
 * @Created by hwj
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";        // 连接符号
    private static final String PREFIX_ENTITY_LIKE = "like:entity";     // 实体点赞前缀
    private static final String PREFIX_USER_LIKE = "like:user";     // 用户获赞前缀

    // 某个实体对应的赞    like:entity:entityType:entityId --> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 用户获赞  like:user:userid --> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }
}
