package com.lesson.community.dao;

import com.lesson.community.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    //查询当前用户的会话列表，针对每个会话只返回一条最新的私信
    @Query(value = "select * from message where id in (select max(id) " +
            "from message where status != 2 and from_id != 1 and (" +
            "from_id = :userid or to_id = :userid) group by conversation_id) order by id " +
            "desc limit :offset, :limit", nativeQuery = true)
    List<MessageEntity> getConversations(@Param("userid") int userid,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    //查询当前用户的会话数量
    @Query(value = "select count(*) from (select  max(id) from message where " +
            "status != 2 and from_id != 1 and (from_id = :userid or to_id = :userid) " +
            "group by conversation_id) as cs", nativeQuery = true)
    int getConversationCount(@Param("userid") int userid);

    // 查询每个会话所包含的私信列表
    @Query(value = "select * from message where status != 2 and from_id != 1 " +
            "and conversation_id = :cvId limit :offset, :limit", nativeQuery = true)
    List<MessageEntity> getLetters(@Param("cvId") String couversationId,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

    // 查询每个会话所包含的私信数量
    @Query(value = "select count(*) from MessageEntity where status != 2 and fromId != 1 " +
            "and conversationId = :converstaionId")
    int getLettersCount(@Param("converstaionId") String conversationId);

    //查询未读私信数量
    @Query(value = "select count(*) from message where status = 0 and from_id != 1" +
            " and to_id = :userid and if(:converstaionId != null, conversation_id = :converstaionId, 1=1)",
            nativeQuery = true)
    int getLetterUnreadCount(@Param("userid") int userid,
                             @Param("converstaionId") String conversationId);

    @Query(value = "update MessageEntity set status = :status where id = :id")
    void updateStatus(@Param("id") int id, @Param("status") int status);
}