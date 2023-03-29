package com.lesson.community.controller;

import com.lesson.community.entity.CommentEntity;
import com.lesson.community.entity.DiscussPostEntity;
import com.lesson.community.entity.PageObject;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.CommentService;
import com.lesson.community.service.DiscussPostService;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.ConstantUtil;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Classname DiscussPostController
 * @Description 管理帖子相关业务
 * @Date 2023/3/27 16:14
 * @Created by hwj
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements ConstantUtil {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private HostHolderUntil holderUntil;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    /**
     * @author hwj
     * @Description 帖子发布
     * @date 2023/3/27 16:24
     */
    @RequestMapping(path = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public String publishPost(String title, String content) {
        System.out.println("进入Controller");
        UserEntity user = holderUntil.getUser();
        if (user == null) {
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
    public String postDetail(@PathVariable("discussPostId") int discussPostId, Model model, PageObject page) {

        // 获取帖子内容
        DiscussPostEntity post = discussPostService.getDiscussPostByID(discussPostId);
        model.addAttribute("post", post);
        // 作者
        UserEntity user = userService.getUserEntityByID(post.getUserId());
        model.addAttribute("user", user);
        // 评论分页信息
        page.setPagePath("/discuss/detail/" + discussPostId);
        page.setTotalRows(post.getCommentCount());
        page.setLimit(5);
        model.addAttribute("page", page);

        // 评论列表
        List<CommentEntity> commentList = commentService.getCommentsByType(ENTITY_TYPE_POST,
                post.getId(), page.getOffset(), page.getLimit());
        if (commentList != null) {
            // 返回页面的帖子列表
            List<Map<String, Object>> cls = new ArrayList<>();
            for (CommentEntity com : commentList) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("comment", com);    // 评论
                tmp.put("user", userService.getUserEntityByID(com.getUserId()));  //评论作者
                // 回复列表
                List<CommentEntity> replyList = commentService.getCommentsByType
                        (ENTITY_TYPE_COMMENT, com.getId(), 0, Integer.MAX_VALUE);
                // 返回页面的回复列表
                List<Map<String, Object>> rls = new ArrayList<>();
                if (replyList != null) {
                    for (CommentEntity rep : replyList) {
                        Map<String, Object> tp = new HashMap<>();
                        tp.put("reply", rep); // 回复
                        tp.put("user", userService.getUserEntityByID(rep.getUserId()));  //回复作者
                        UserEntity target = rep.getTargetId() == 0 ?
                                null : userService.getUserEntityByID(rep.getTargetId());
                        tp.put("target", target);  // 回复对象
                        rls.add(tp);
                    }
                }
                tmp.put("replies", rls);
                // 回复数量
                tmp.put("replyCount", commentService.getCountByType(ENTITY_TYPE_COMMENT, com.getId()));

                cls.add(tmp);
            }
            model.addAttribute("comments", cls);
        }

        return "/site/discuss-detail";
    }
}
