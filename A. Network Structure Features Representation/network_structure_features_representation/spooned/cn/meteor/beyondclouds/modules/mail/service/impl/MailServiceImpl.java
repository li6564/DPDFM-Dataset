package cn.meteor.beyondclouds.modules.mail.service.impl;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
/**
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Service
public class MailServiceImpl implements cn.meteor.beyondclouds.modules.mail.service.IMailService {
    private org.springframework.mail.javamail.JavaMailSender mailSender;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public MailServiceImpl(org.springframework.mail.javamail.JavaMailSender mailSender, cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.mailSender = mailSender;
        this.redisHelper = redisHelper;
    }

    @java.lang.Override
    public void sendSimpleMail(cn.meteor.beyondclouds.modules.mail.dto.EmailDTO email) {
        try {
            org.springframework.mail.SimpleMailMessage message = new org.springframework.mail.SimpleMailMessage();
            message.setFrom(email.getFrom());
            message.setTo(email.getTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            mailSender.send(message);
        } catch (java.lang.Exception e) {
            log.error("发送邮件异常！", e.getMessage());
            e.printStackTrace();
        }
    }

    @java.lang.Override
    public void sendHtmlMail(cn.meteor.beyondclouds.modules.mail.dto.EmailDTO email) {
        javax.mail.internet.MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(message, true);
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            // 发送htmltext值需要给个true，不然不生效
            helper.setText(email.getText(), true);
            mailSender.send(message);
        } catch (java.lang.Exception e) {
            log.error("发送邮件异常！", e.getMessage());
        }
    }

    @java.lang.Override
    public void sendVerifyCode(java.lang.String email, java.lang.String randomVerifyCode) {
        // 1. 发送验证码
        cn.meteor.beyondclouds.modules.mail.dto.EmailDTO emailDTO = new cn.meteor.beyondclouds.modules.mail.dto.EmailDTO("13546386889@163.com", email, "云里云外开源社区-验证码", cn.meteor.beyondclouds.modules.mail.util.EmailUtils.generateVerifyCodeMail(randomVerifyCode));
        sendHtmlMail(emailDTO);
        // 2.存储验证码到redis
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.EMAIL_VERIFY_CODE(email), randomVerifyCode, 5 * 60);
    }
}