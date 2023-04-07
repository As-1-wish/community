package com.lesson.community.service.impl;

import com.lesson.community.service.FollowService;
import com.lesson.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @Classname FollowServiceImpl
 * @Description 关注业务层
 * @Date 2023/4/7 16:19
 * @Created by hwj
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void followOpe(int preUserId, int userId, boolean tag) {
        //preUserId--->A   userId--->B   A 关注 B
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String fansKey = RedisKeyUtil.getFansKey(userId); // B的粉丝列表
                String followedKey = RedisKeyUtil.getFollowedKey(preUserId);  // A 的关注列表

                operations.multi();
                if(tag){
                    operations.opsForZSet().add(fansKey, preUserId, System.currentTimeMillis());
                    operations.opsForZSet().add(followedKey, userId, System.currentTimeMillis());
                }
                else {
                    operations.opsForZSet().remove(fansKey, preUserId);
                    operations.opsForZSet().remove(followedKey, userId);
                }

                return operations.exec();
            }
        });
    }
}
