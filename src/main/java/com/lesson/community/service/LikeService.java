package com.lesson.community.service;

public interface LikeService {

    /**
     * @author hwj
     * @Description 点赞操作记录,以及记录用户获赞
     * @date 2023/4/7 9:55
     */
    void like(int userId, int entityType, int entityId, int entityUserId);

    /**
     * @author hwj
     * @Description 查询某实体点赞的数量
     * @date 2023/4/7 10:05
     */
    Long getLikeCount(int entityType, int entityId);

    /**
     * @author hwj
     * @Description 查询某人对某实体的点赞状态 1-点赞 0-未点赞
     * @date 2023/4/7 10:11
     */
    Integer getLikeStatus(int userId, int entityType, int entityId);

    /**
     * @author hwj
     * @Description 查询用户获赞数量
     * @date 2023/4/7 13:45
     */
    int getUserLikeCount(int userId);
}
