package com.lesson.community.service.impl;

import com.lesson.community.dao.DiscussPostDao;
import com.lesson.community.entity.DiscussPostEntity;
import com.lesson.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<DiscussPostEntity> getDiscussPosts(int userid, int offset, int limit) {
        return discussPostDao.getDiscussPosts(userid, offset, limit);
    }

    @Override
    public int getDiscussPostRows(int userid) {
        return discussPostDao.getDiscussPostRows(userid);
    }
}
