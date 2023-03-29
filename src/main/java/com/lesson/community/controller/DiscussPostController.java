package com.lesson.community.controller;

import com.lesson.community.entity.DiscussPostEntity;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.DiscussPostService;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Classname DiscussPostController
 * @Description 管理帖子相关业务
 * @Date 2023/3/27 16:14
 * @Created by hwj
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolderUntil holderUntil;

    @Autowired
    private UserService userService;

    /**
     * @author hwj
     * @Description 帖子发布
     * @date 2023/3/27 16:24
     */
    @RequestMapping(path = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public String publishPost(String title, String content){
        System.out.println("进入Controller");
        UserEntity user = holderUntil.getUser();
        if(user==null){
            return CommunityUtil.getJSONString(403, "你还没有登录哦！");
        }
        DiscussPostEntity discussPost = new DiscussPostEntity();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Timestamp(new Date().getTime()));
        discussPostService.insertDiscussPost(discussPost);

        return CommunityUtil.getJSONString(0, "发布成功！");
    }

    /**
     * @author hwj
     * @Description 帖子详情页面
     * @date 2023/3/27 17:30
     */
    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String postDetail(@PathVariable("discussPostId") int discussPostId, Model model){

        // 获取帖子内容
        DiscussPostEntity post = discussPostService.getDiscussPostByID(discussPostId);
        model.addAttribute("post", post);
        // 作者
        UserEntity user = userService.getUserEntityByID(post.getUserId());
        model.addAttribute("user", user);

        System.out.println(post);
        System.out.println(user);

        return "/site/discuss-detail";
    }
}
