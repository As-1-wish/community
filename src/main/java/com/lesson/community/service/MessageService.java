package com.lesson.community.service;


import com.lesson.community.entity.MessageEntity;

import java.util.List;

public interface MessageService {
    //查询当前用户的会话列表，针对每个会话只返回一条最新的私信
    List<MessageEntity> getConversations(int userid, int offset, int limit);

    //查询当前用户的会话数量
    int getConversationCount(int userid);

    // 查询每个会话所包含的私信列表
    List<MessageEntity> getLetters(String couversationId, int offset, int limit);

    // 查询每个会话所包含的私信数量
    int getLettersCount(String conversationId);

    //查询未读私信数量
    int getLetterUnreadCount(int userid, String conversationId);
}
