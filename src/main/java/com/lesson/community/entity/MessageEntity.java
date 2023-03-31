package com.lesson.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11) NOT NULL")
    private int id;

    @Column(name = "from_id", columnDefinition = "int(11) DEFAULT NULL")
    private int fromId;

    @Column(name = "to_id", columnDefinition = "varchar(45) DEFAULT NULL")
    private int toId;

    @Column(name = "conversation_id", columnDefinition = "varchar(45) NOT NULL")
    private String conversationId;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "status", columnDefinition = "int(11) DEFAULT '0' comment '0-未读;1-已读;2-删除'")
    private int status;

    @Column(name = "createTime", columnDefinition = "timestamp NULL DEFAULT NULL")
    private Timestamp createTime;


    public MessageEntity(){}

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", conversationId='" + conversationId + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
