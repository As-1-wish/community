package com.lesson.community.service.impl;

import com.lesson.community.dao.CommentRepository;
import com.lesson.community.entity.CommentEntity;
import com.lesson.community.service.CommentService;
import com.lesson.community.service.DiscussPostService;
import com.lesson.community.util.ConstantUtil;
import com.lesson.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Classname CommentServiceImpl
 * @Description 评论业务实现
 * @Date 2023/3/29 14:28
 * @Created by hwj
 */
@Service
public class CommentServiceImpl implements CommentService, ConstantUtil {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<CommentEntity> getCommentsByType(int EntityType, int EntityId, int offset, int limit) {
        return commentRepository.getCommentsByType(EntityType, EntityId, offset, limit);
    }

    @Override
    public int getCountByType(int EntityType, int EntityId) {
        return commentRepository.getCountByType(EntityType, EntityId);
    }

    /**
     * @author hwj
     * @Description 添加评论，同时更新对应帖子的评论数量
     * @date 2023/3/29 18:48
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public void insertComment(CommentEntity commentEntity) {

        if(commentEntity==null)
            throw new IllegalArgumentException("参数不能为空！");

        commentEntity.setContent(HtmlUtils.htmlEscape(commentEntity.getContent()));
        commentEntity.setContent(sensitiveFilter.filter(commentEntity.getContent()));

        // 更新帖子评论数量
        if(commentEntity.getEntityType() == ENTITY_TYPE_POST){
            int count = commentRepository.getCountByType(ENTITY_TYPE_POST, commentEntity.getEntityId());
            discussPostService.updateCommentCount(commentEntity.getEntityId(), count);
        }


        commentRepository.saveAndFlush(commentEntity);
    }

}
