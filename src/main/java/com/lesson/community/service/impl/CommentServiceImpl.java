package com.lesson.community.service.impl;

import com.lesson.community.dao.CommentRepository;
import com.lesson.community.entity.CommentEntity;
import com.lesson.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname CommentServiceImpl
 * @Description 评论业务实现
 * @Date 2023/3/29 14:28
 * @Created by hwj
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentEntity> getCommentsByType(int EntityType, int EntityId, int offset, int limit) {
        return commentRepository.getCommentsByType(EntityType, EntityId, offset, limit);
    }

    @Override
    public int getCountByType(int EntityType, int EntityId) {
        return commentRepository.getCountByType(EntityType, EntityId);
    }
}
