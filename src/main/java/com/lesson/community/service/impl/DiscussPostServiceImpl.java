package com.lesson.community.service.impl;

import com.lesson.community.dao.DiscussPostDao;
import com.lesson.community.entity.DiscussPostEntity;
import com.lesson.community.service.DiscussPostService;
import com.lesson.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Classname DiscussPostServiceImpl
 * @Description
 * @Date 2023/3/20 14:49
 * @Created by hwj
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostDao discussPostDao;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<DiscussPostEntity> getDiscussPosts(int userid, int offset, int limit) {
        return discussPostDao.getDiscussPosts(userid, offset, limit);
    }

    @Override
    public int getDiscussPostRows(int userid) {
        return discussPostDao.getDiscussPostRows(userid);
    }

    /**
     * @author hwj
     * @Description 帖子插入
     * @date 2023/3/27 16:13
     */
    @Override
    public void insertDiscussPost(DiscussPostEntity discussPost) {
        if(discussPost==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        // 转义HTML标记
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        // 敏感词过滤
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));

        discussPostDao.saveAndFlush(discussPost);
    }

    @Override
    public DiscussPostEntity getDiscussPostByID(int id) {
        return discussPostDao.getDiscussPostById(id);
    }


}
