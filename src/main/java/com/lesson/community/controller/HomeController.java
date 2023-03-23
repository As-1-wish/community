package com.lesson.community.controller;

import com.lesson.community.entity.DiscussPostEntity;
import com.lesson.community.entity.PageObject;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.DiscussPostService;
import com.lesson.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname HomeController
 * @Description 首页控制
 * @Date 2023/3/20 15:36
 * @Created by hwj
 */
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    /**
     * @param model 负责在 Controller 与 View 之间传递数据
     * @return String
     * @author hwj
     * @Description 初始化首页, 查询 limit(受page实体影响) 条帖子显示在首页
     * @date 2023/3/20 15:51
     */
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, PageObject page) {
        //方法调用前, SpringMVC 会自动实例化 Model 和 Page, 并将 Page 注入 Model
        //所以在 thymeleaf 中可以直接访问 Page 对象中的数据
        page.setTotalRows(discussPostService.getDiscussPostRows(0));
        page.setPagePath("/index");
        //查询到的帖子
        List<DiscussPostEntity> postList = discussPostService.
                getDiscussPosts(0, page.getOffset(), page.getLimit());
        //最终的结果集
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (!postList.isEmpty()) {
            for (DiscussPostEntity dis : postList) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", dis); // 将帖子对象存入,对应 post
                UserEntity user = userService.getUserEntityByID(dis.getUserId());
                map.put("user", user); // 将帖子对应的用户对象存入,对应 user
                discussPosts.add(map); // 将处理好的单个帖子-用户存入结果集
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("page", page);
        System.out.println(page.getPageRange().getKey());
        System.out.println(page.getPageRange().getValue());
        return "/index";
    }
}
