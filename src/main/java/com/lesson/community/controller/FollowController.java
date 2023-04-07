package com.lesson.community.controller;

import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.FollowService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname FollowController
 * @Description 关注业务控制层
 * @Date 2023/4/7 16:46
 * @Created by hwj
 */
@Controller
public class FollowController {

    @Autowired
    private HostHolderUntil holderUntil;

    @Autowired
    private FollowService followService;

    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    @ResponseBody
    public String followOpes(int userId, boolean tag) {
        UserEntity user = holderUntil.getUser();

        // tag 为真则添加关注，反之则解除关注
        followService.followOpe(user.getId(), userId, tag);

        return CommunityUtil.getJSONString(0, tag ? "已关注!" : "已取消关注");
    }
}
