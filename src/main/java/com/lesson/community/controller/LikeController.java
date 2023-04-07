package com.lesson.community.controller;

import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.LikeService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LikeController
 * @Description 点赞相关控制
 * @Date 2023/4/7 10:13
 * @Created by hwj
 */
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolderUntil holderUntil;

    /**
     * @author hwj
     * @Description 进行点赞操作,返回点赞信息
     * @date 2023/4/7 10:25
     */
    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId) {
        UserEntity user = holderUntil.getUser();

        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        // 获取点赞数量
        long likeCount = likeService.getLikeCount(entityType, entityId);
        // 获取点赞状态
        int likeStatus = likeService.getLikeStatus(user.getId(), entityType, entityId);

        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        return CommunityUtil.getJSONString(0, null, map);
    }
}
