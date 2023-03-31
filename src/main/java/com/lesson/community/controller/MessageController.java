package com.lesson.community.controller;

import com.lesson.community.entity.MessageEntity;
import com.lesson.community.entity.PageObject;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.MessageService;
import com.lesson.community.util.HostHolderUntil;
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

    // 私信列表
    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)
    public String getLetterList(Model model, PageObject page){
        UserEntity user = holderUntil.getUser();
        // 分页列表
        page.setPagePath("/letter/list");
        page.setTotalRows(messageService.getConversationCount(user.getId()));
        page.setLimit(5);
        model.addAttribute("page",page);
        // 会话列表
        List<MessageEntity> conversationList = messageService.getConversations(
                user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if(conversationList!=null){
            for(MessageEntity message : conversationList){
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("conversation", message);   //当前会话内容
                // 此会话信息数量
                tmp.put("letterCount", messageService.getLettersCount(message.getConversationId()));
                // 此会话未读信息数量
                tmp.put("unreadCount", messageService.getLetterUnreadCount(user.getId(),
                        message.getConversationId()));
                int targetId = user.getId() == message.getFromId() ? message.getToId():message.getFromId();
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
}
