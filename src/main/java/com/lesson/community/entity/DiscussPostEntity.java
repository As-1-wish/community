package com.lesson.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

//帖子信息类
@Entity
@Data
@Table(name = "discuss_post")
public class DiscussPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11) NOT NULL")
    private int id;

    @Column(name = "user_id", columnDefinition = "int(11) DEFAULT NULL")
    private int userId;

    @Column(name = "title", columnDefinition = "varchar(100) DEFAULT NULL")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "type", columnDefinition = "int(11) DEFAULT '0' comment '0-普通;1-置顶'")
    private int type;

    @Column(name = "status", columnDefinition = "int(11) DEFAULT '0' comment '0-正常;1-精华;2-拉黑'")
    private int status;

    @Column(name = "create_time", columnDefinition = "timestamp NULL DEFAULT NULL")
    private Timestamp createTime;

    @Column(name = "comment_count", columnDefinition = "int(11) DEFAULT NULL")
    private int commentCount;

    @Column(name = "score", columnDefinition = "double DEFAULT NULL")
    private double score;

    public DiscussPostEntity() {
    }

    public DiscussPostEntity(int userId, String title, String content, int type,
                             int status, Timestamp createTime, int commentCount, double score) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
        this.commentCount = commentCount;
        this.score = score;
    }

    @Override
    public String toString() {
        return "DiscussPostEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commentCount=" + commentCount +
                ", score=" + score +
                '}';
    }
}
