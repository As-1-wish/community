package com.lesson.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @Classname MailClient
 * @Description 邮箱客户端
 * @Date 2023/3/22 14:17
 * @Created by hwj
 */
@Service
public class MailClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(MailClientUtil.class);
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * @author hwj
     * @Description 邮件发送函数
     * @date 2023/3/22 15:52
     * @param to 接收人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void senMail(String to, String subject, String content) {
        try {
            // JavaMainlSender 通过构建出的 mimeMessage 发送邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 利用 MimeMessageHelper 构建 mimeMessage
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setFrom(from);        // 发送人
            mimeMessageHelper.setTo(to);            // 接收人
            mimeMessageHelper.setSubject(subject);  // 邮件主题
            mimeMessageHelper.setText(content, true);     // 邮件内容

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("邮件发送失败:" + e.getMessage());
        }
    }
}
