package com.lesson.community.service;


import com.lesson.community.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    List<CommentEntity> getCommentsByType(int EntityType, int EntityId, int offset, int limit);

    int getCountByType(int EntityType, int EntityId);
}
