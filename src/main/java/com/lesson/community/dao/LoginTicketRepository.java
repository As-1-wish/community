package com.lesson.community.dao;

import com.lesson.community.entity.LoginTicketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Timestamp;

/**
 * @Classname LoginTicketRepository
 * @Description 登录令牌操作处理
 * @Date 2023/3/23 13:49
 * @Created by hwj
 */
@Repository
public class LoginTicketRepository {

    private static final Logger logger = LoggerFactory.getLogger(LoginTicketRepository.class);
    @Autowired
    private EntityManager entityManager;

    /**
     * @param loginTicket 待插入对象
     * @author hwj
     * @Description LoginTicket实体插入
     * @date 2023/3/23 14:07
     */
    @Transactional
    public void saveAndFlush(LoginTicketEntity loginTicket) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into login_ticket(user_id, ticket, status, expired) values(").append(loginTicket.getUserId())
                .append(",'").append(loginTicket.getTicket()).append("',")
                .append(loginTicket.getStatus()).append(",'").append(loginTicket.getExpired())
                .append("')");

        try {
            Query query = entityManager.createNativeQuery(sql.toString());
            query.executeUpdate();
        } catch (Exception e) {
            logger.error("LoginEntity 数据插入失败:" + e.getMessage());
        }
    }

    /**
     * @param ticket 令牌
     * @author hwj
     * @Description 根据ticket查询对应登录信息
     * @date 2023/3/23 14:18
     */
    public LoginTicketEntity getOneByTicket(String ticket) {
        LoginTicketEntity loginTicket = new LoginTicketEntity();
        String sql = String.format("select * from login_ticket where ticket = '%s'", ticket);

        Object[] obs = new Object[0];
        try {
            Query query = entityManager.createNativeQuery(sql);
            obs = (Object[]) query.getSingleResult();
        } catch (Exception e) {
            logger.error("查询LoginTicket失败:" + e.getMessage());
        }

        loginTicket.setId((Integer) obs[0]);
        loginTicket.setUserId((Integer) obs[1]);
        loginTicket.setTicket((String) obs[2]);
        loginTicket.setStatus((Integer) obs[3]);
        loginTicket.setExpired((Timestamp) obs[4]);

        return loginTicket;
    }

    /**
     * @author hwj
     * @Description 根据ticket更新status
     * @date 2023/3/23 14:34
     */
    @Transactional
    public void UpdateStatus(String ticket, int stauts) {
        String sql = String.format("update login_ticket set status = %d where ticket = '%s'", stauts, ticket);

        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();

    }

}
