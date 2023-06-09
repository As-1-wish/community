package com.lesson.community.controller;

import com.lesson.community.annotation.LoginRequired;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.LikeService;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.HostHolderUntil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Classname UserController
 * @Description 用户相关业务, 如用户信息设置
 * @Date 2023/3/24 10:25
 * @Created by hwj
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolderUntil holderUntil;

    @Autowired
    private LikeService likeService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    /**
     * @author hwj
     * @Description 修改用户头像信息, 并接收用户上传的头像文件
     * @date 2023/3/24 11:25
     */
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片！");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        // 获取后缀名
        String suffix = fileName != null ? (fileName.substring(fileName.lastIndexOf("."))) : "";
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确！");
            return "/site/setting";
        }

        // 生成随机的文件名
        fileName = CommunityUtil.getUUID() + suffix;
        // 确认文件存储的路径
        File storagePath = new File(uploadPath + "/" + fileName);

        try {
            headerImage.transferTo(storagePath);
        } catch (IOException e) {
            logger.error("上传文件失败:" + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常！" + e);
        }

        //更新当前用户的头像的访问路径(Web访问路径)
        //https://localhost:8081/community/user/header/filename
        UserEntity user = holderUntil.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.UpdateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    /**
     * @author hwj
     * @Description 从服务器读取用户头像
     * @date 2023/3/24 11:37
     */
    @RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response) {
        // 头像的服务器存放路径
        filename = uploadPath + "/" + filename;
        // 文件后缀
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(filename)
        ) {
            byte[] buffer = new byte[1024];
            int b;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }

        } catch (IOException e) {
            logger.error("读取头像失败:" + e.getMessage());
        }
    }

    /**
     * @author hwj
     * @Description 修改密码
     * @date 2023/4/7 13:51
     */
    @LoginRequired
    @RequestMapping(path = "/pwd", method = RequestMethod.POST)
    public String ModifyPassword(String old_password, String new_password, String confirm_password, Model model){
        UserEntity user = holderUntil.getUser();

        if(!new_password.equals(confirm_password)){
            model.addAttribute("repwdError", "两次输入密码不一致！");
            return "/site/setting";
        }
        if(!user.getPassword().equals(CommunityUtil.Encrypt(old_password + user.getSalt()))){
            model.addAttribute("pwdError", "原密码输入错误！");
            return "/site/setting";
        }
        if(old_password.equals(new_password)){
            model.addAttribute("repwdError", "新密码不能与旧密码一致！");
            model.addAttribute("oldpwd", old_password);
            return "/site/setting";
        }
        userService.ModifyPassword(user.getId(), new_password);
        return "redirect:/index";
    }

    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfile(@PathVariable("userId") int userId, Model model){
        // 用户信息
        UserEntity user = userService.getUserEntityByID(userId);
        model.addAttribute("user", user);
        // 用户获赞
        long likeCount = likeService.getUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);


        return "/site/profile";
    }
}
