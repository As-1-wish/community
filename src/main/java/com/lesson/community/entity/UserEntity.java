package com.lesson.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "idKey") //id自增策略
    @TableGenerator(
            name = "idKey",
            table = "primarykeys",  //用来自定义主键生成表的表名称
            pkColumnName = "pre_Key", //指定主键生成表的主键列的名称
            valueColumnName = "prekey_value", //用来自定义在主键生成表中存储ID的列名称
            pkColumnValue = "user_key",  //为当前主键生成器指定在生成器表中的主键值,用于将这组生成的值与可能存储在表中的其他值区分开来
            allocationSize = 1   //每次获取主键增量值,默认为50
           // initialValue = 1000   //初始化主键值
    )
    @Column(name = "id", columnDefinition = "int(11) NOT NULL comment '用户ID'")
    private int id;

    @Column(name = "username", columnDefinition = "varchar(50) DEFAULT NULL comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(50) DEFAULT NULL comment '用户密码'")
    private String password;

    @Column(name = "salt", columnDefinition = "varchar(50) DEFAULT NULL comment '密码后缀,防止密码简单'")
    private String salt;

    @Column(name = "email", columnDefinition = "varchar(100) DEFAULT NULL comment '邮箱'")
    private String email;

    @Column(name = "type", columnDefinition = "int(11) DEFAULT NULL comment '0-普通用户;1-管理员;2-版主'")
    private int type;

    @Column(name = "status", columnDefinition = "int(11) DEFAULT NULL comment '0-未激活;1-已激活'")
    private int status;

    @Column(name = "activation_code", columnDefinition = "varchar(100) DEFAULT NULL")
    private String activationCode;

    @Column(name = "header_url", columnDefinition = "varchar(200) DEFAULT NULL")
    private String headerUrl;

    @Column(name = "create_time", columnDefinition = "timestamp DEFAULT NULL")
    private Timestamp createTime;

    public UserEntity(String username, String password, String salt,
                      String email, int type, int status, String activationCode,
                      String headerUrl, Timestamp createTime) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.type = type;
        this.status = status;
        this.activationCode = activationCode;
        this.headerUrl = headerUrl;
        this.createTime = createTime;
    }

    public UserEntity() {}

    @Override
    public String   toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", activationCode='" + activationCode + '\'' +
                ", headerUrl='" + headerUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
