package com.lesson.community.service.impl;

import com.lesson.community.service.LikeService;
import com.lesson.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

/**
 * @Classname LikeServiceImpl
 * @Description 点赞相关业务
 * @Date 2023/4/7 9:54
 * @Created by hwj
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void like(int userId, int entityType, int entityId, int entityUserId) {
        // redis 事务原子操作
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                Boolean liked = operations.opsForSet().isMember(entityKey, userId);
                operations.multi(); // 开启事务
                if (Boolean.TRUE.equals(liked)) {
                    operations.opsForSet().remove(entityKey, userId);
                    operations.opsForValue().decrement(userKey);
                } else {
                    operations.opsForSet().add(entityKey, userId);
                    operations.opsForValue().increment(userKey);
                }

                return operations.exec();  // 提交事务
            }
        });
    }

    public Long getLikeCount(int entityType, int entityId) {
        String entityKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityKey);
    }

    public Integer getLikeStatus(int userId, int entityType, int entityId) {
        String entityKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(entityKey, userId)) ? 1 : 0;
    }

    @Override
    public int getUserLikeCount(int userId) {
        String userKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userKey);
        return count == null ? 0 : count;
    }
}
