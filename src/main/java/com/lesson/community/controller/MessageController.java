package com.lesson.community.controller;

import com.lesson.community.entity.MessageEntity;
import com.lesson.community.entity.PageObject;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.MessageService;
import com.lesson.community.service.UserService;
import com.lesson.community.util.HostHolderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname MessageController
 * @Description
 * @Date 2023/3/31 14:07
 * @Created by hwj
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private HostHolderUntil holderUntil;

    @Autowired
    private UserService userService;

    /**
     * @author hwj
     * @Description 显示私信列表
     * @date 2023/3/31 15:48
     */
    // 会话列表
    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
    public String getLetterList(Model model, PageObject page) {
        UserEntity user = holderUntil.getUser();
        // 分页列表
        page.setPagePath("/letter/list");
        page.setTotalRows(messageService.getConversationCount(user.getId()));
        page.setLimit(5);
        model.addAttribute("page", page);
        // 会话列表
        List<MessageEntity> conversationList = messageService.getConversations(
                user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (MessageEntity message : conversationList) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("conversation", message);   //当前会话内容
                // 此会话信息数量
                tmp.put("letterCount", messageService.getLettersCount(message.getConversationId()));
                // 此会话未读信息数量
                tmp.put("unreadCount", messageService.getLetterUnreadCount(user.getId(),
                        message.getConversationId()));
                int targetId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                // 信息发送对象
                tmp.put("target", targetId);
                conversations.add(tmp);
            }
        }
        model.addAttribute("conversations", conversations);

        // 查询总的未读消息数量
        int letterUnreadCount = messageService.getLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);


        return "/site/letter";
    }

    @RequestMapping(path = "/letter/detail/{conversationId}", method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId,
                                  PageObject page, Model model) {
        // 分页设置
        page.setLimit(5);
        page.setPagePath("/letter/detail/" + conversationId);
        page.setTotalRows(messageService.getLettersCount(conversationId));
        model.addAttribute("page", page);

        //私信列表
        List<MessageEntity> letterList = messageService.getLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> letters = new ArrayList<>();
        if (letterList != null) {
            for (MessageEntity mes : letterList) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("letter", mes);
                tmp.put("fromUser", userService.getUserEntityByID(mes.getFromId()));

                letters.add(tmp);
            }
        }
        model.addAttribute("letters", letters);

        // 私信目标
        model.addAttribute("target", getLetterTarget(conversationId));

        return "/site/letter-detail";
    }

    /**
     * @author hwj
     * @Description 利用会话 ID获取私信目标
     * @date 2023/4/4 16:59
     */
    public UserEntity getLetterTarget(String conversationId) {
        String[] str = conversationId.split("_");
        int id0 = Integer.parseInt(str[0]);
        int id1 = Integer.parseInt(str[1]);
        return holderUntil.getUser().getId() != id0 ?
                userService.getUserEntityByID(id1) : userService.getUserEntityByID(id0);
    }
}
