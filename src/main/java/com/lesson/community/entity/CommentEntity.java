package com.lesson.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11) NOT NULL")
    private int id;

    @Column(name = "user_id", columnDefinition = "int(11) DEFAULT NULL")
    private int userId;

    @Column(name = "entity_type", columnDefinition = "int(11) DEFAULT NULL")
    private int entityType;

    @Column(name = "entity_id", columnDefinition = "int(11) DEFAULT NULL")
    private int entityId;

    @Column(name = "target_id", columnDefinition = "int(11) DEFAULT NULL")
    private int targetId;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "status", columnDefinition = "int(11) DEFAULT '0'")
    private int status;

    @Column(name = "create_time", columnDefinition = "timestamp NULL DEFAULT NULL")
    private Timestamp createTime;

    public CommentEntity(int userId, int entityType, int entityId, int targetId, String content,
                         int status, Timestamp createTime) {
        this.userId = userId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.targetId = targetId;
        this.content = content;
        this.status = status;
        this.createTime = createTime;
    }

    public CommentEntity() {}

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", targetId=" + targetId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
