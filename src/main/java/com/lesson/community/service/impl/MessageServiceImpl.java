package com.lesson.community.service.impl;

import com.lesson.community.dao.MessageRepository;
import com.lesson.community.entity.MessageEntity;
import com.lesson.community.service.MessageService;
import com.lesson.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @Classname MessageServiceImpl
 * @Description 会话列表业务
 * @Date 2023/3/31 11:51
 * @Created by hwj
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<MessageEntity> getConversations(int userid, int offset, int limit) {
        return messageRepository.getConversations(userid, offset, limit);
    }

    @Override
    public int getConversationCount(int userid) {
        return messageRepository.getConversationCount(userid);
    }

    @Override
    public List<MessageEntity> getLetters(String couversationId, int offset, int limit) {
        return messageRepository.getLetters(couversationId, offset, limit);
    }

    @Override
    public int getLettersCount(String conversationId) {
        return messageRepository.getLettersCount(conversationId);
    }

    @Override
    public int getLetterUnreadCount(int userid, String conversationId) {
        String sql = "select count(*) from message where status = 0 and from_id != 0 and to_id = "
                + userid + (conversationId != null?" and conversation_id = '" + conversationId+"'":"");
        System.out.println(sql);
        Query query = entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        System.out.println(result.toString());
        return Integer.parseInt(result.toString());
    }

    @Override
    public void insertLetter(MessageEntity message) {

        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));

        messageRepository.saveAndFlush(message);
    }

    @Override
    public void updateStatus(List<Integer> ids, int stauts) {
        for (int id : ids)
            messageRepository.updateStatus(id, stauts);
    }
}
