package com.lesson.community.controller;

import com.lesson.community.entity.CommentEntity;
import com.lesson.community.service.CommentService;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Classname CommentController
 * @Description 评论相关业务
 * @Date 2023/3/29 18:49
 * @Created by hwj
 */
@Controller
@RequestMapping(path = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolderUntil holderUntil;

    @RequestMapping(path = "/add/{discussId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussId") int discussId, CommentEntity comment){

        comment.setUserId(holderUntil.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Timestamp(new Date().getTime()));
        commentService.insertComment(comment);

        return "redirect:/discuss/detail/" + discussId;
    }
}
