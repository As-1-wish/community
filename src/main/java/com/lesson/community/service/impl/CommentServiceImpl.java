package com.lesson.community.service.impl;

import com.lesson.community.dao.CommentDao;
import com.lesson.community.entity.CommentEntity;
import com.lesson.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public void saveAndFlush(CommentEntity commentEntity) {

    }
}
