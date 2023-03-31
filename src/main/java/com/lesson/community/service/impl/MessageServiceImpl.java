package com.lesson.community.service.impl;

import com.lesson.community.dao.MessageRepository;
import com.lesson.community.entity.MessageEntity;
import com.lesson.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return messageRepository.getLetterUnreadCount(userid, conversationId);
    }
}
