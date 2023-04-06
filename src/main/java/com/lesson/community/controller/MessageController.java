package com.lesson.community.controller;

import com.lesson.community.entity.MessageEntity;
import com.lesson.community.entity.PageObject;
import com.lesson.community.entity.UserEntity;
import com.lesson.community.service.MessageService;
import com.lesson.community.service.UserService;
import com.lesson.community.util.CommunityUtil;
import com.lesson.community.util.HostHolderUntil;
import org.apache.catalina.User;
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
        UserEntity preUser = holderUntil.getUser();
        // 分页列表
        page.setPagePath("/letter/list");
        page.setTotalRows(messageService.getConversationCount(preUser.getId()));
        page.setLimit(5);
        model.addAttribute("page", page);
        // 会话列表
        List<MessageEntity> conversationList = messageService.getConversations(
                preUser.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (MessageEntity message : conversationList) {
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("conversation", message);   //当前会话内容
                // 此会话信息数量
                tmp.put("letterCount", messageService.getLettersCount(message.getConversationId()));
                // 此会话未读信息数量
                tmp.put("unreadCount", messageService.getLetterUnreadCount(preUser.getId(),
                        message.getConversationId()));

                int targetId = preUser.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                // 信息发送对象
                tmp.put("target", userService.getUserEntityByID(targetId));
                conversations.add(tmp);
            }
        }
        model.addAttribute("conversations", conversations);

        // 查询总的未读消息数量
        int letterUnreadCount = messageService.getLetterUnreadCount(preUser.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);


        return "/site/letter";
    }

    /**
     * @author hwj
     * @Description 会话详情
     * @date 2023/4/6 9:37
     */
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

        List<Integer> ids = getUnreadLetters(letterList);
        if (!ids.isEmpty())
            messageService.updateStatus(ids, 1);

        return "/site/letter-detail";
    }

    /**
     * @author hwj
     * @Description 获取未读信息id列表
     * @date 2023/4/6 10:41
     */
    private List<Integer> getUnreadLetters(List<MessageEntity> letters) {
        List<Integer> ids = new ArrayList<>();
        if (letters != null)
            for (MessageEntity message : letters)
                if (message.getToId() == holderUntil.getUser().getId() && message.getStatus() == 0)
                    ids.add(message.getId());
        return ids;
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
                userService.getUserEntityByID(id0) : userService.getUserEntityByID(id1);
    }

    /**
     * @author hwj
     * @Description 给某人发私信
     * @date 2023/4/6 9:06
     */
    @RequestMapping(path = "/letter/send", method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName, String content) {
        UserEntity preUser = holderUntil.getUser();
        UserEntity user = userService.getUserEntityByName(toName);
        if (user == null)
            return CommunityUtil.getJSONString(1, "目标用户不存在");

        MessageEntity message = new MessageEntity();
        message.setFromId(holderUntil.getUser().getId());
        message.setToId(user.getId());
        message.setContent(content);
        message.setConversationId(preUser.getId() > user.getId() ?
                user.getId() + "_" + preUser.getId() : preUser.getId() + "_" + user.getId());
        message.setStatus(0);
        message.setCreateTime(new Timestamp(new Date().getTime()));

        messageService.insertLetter(message);

        return CommunityUtil.getJSONString(0);
    }
}
