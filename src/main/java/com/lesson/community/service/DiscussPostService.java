package com.lesson.community.service;

import com.lesson.community.entity.DiscussPostEntity;

import java.util.List;

public interface DiscussPostService {

    /**
     * @author hwj
     * @Description 获取所有帖子列表,以分页形式
     * @date 2023/3/20 14:45
     * @param userid 帖子拥有者ID,如果为0说明是首页帖子
     * @param offset 起始行行号
     * @param limit  返回的最大行数
     * @return List<DiscussPostEntity>
     */
    List<DiscussPostEntity> getDiscussPosts(int userid, int offset, int limit);

    /**
     * @author hwj
     * @Description 获取行数
     * @date 2023/3/20 14:48
     * @param userid 帖子拥有者ID,如果为0说明是首页帖子
     * @return int
     */
    int getDiscussPostRows(int userid);

    void insertDiscussPost(DiscussPostEntity discussPostEntity);

    DiscussPostEntity getDiscussPostByID(int id);

    int updateCommentCount(int id, int count);
}
