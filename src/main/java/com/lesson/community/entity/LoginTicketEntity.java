package com.lesson.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "login_ticket")
public class LoginTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11) NOT NULL")
    private int id;

    @Column(name = "user_id", columnDefinition = "int(11) DEFAULT NULL")
    private int userId;

    @Column(name = "ticket", columnDefinition = "varchar(45) DEFAULT NULL")
    private String ticket;

    @Column(name = "status", columnDefinition = "int(11) DEFAULT '0' comment '0-有效;1-无效'")
    private int status;

    @Column(name = "expired", columnDefinition = "timestamp NULL DEFAULT NULL")
    private Timestamp expired;

    public LoginTicketEntity() {
    }

    public LoginTicketEntity(int userId, String ticket, int status, Timestamp expired) {
        this.userId = userId;
        this.ticket = ticket;
        this.status = status;
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicketEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
