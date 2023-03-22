package com.lesson.community.service;

import com.lesson.community.entity.CommentEntity;

public interface CommentService {

    public void saveAndFlush(CommentEntity commentEntity);
}
