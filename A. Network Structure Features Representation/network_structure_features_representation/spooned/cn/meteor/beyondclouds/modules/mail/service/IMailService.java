package cn.meteor.beyondclouds.modules.mail.service;
/**
 * 邮件服务
 *
 * @author meteor
 */
public interface IMailService {
    /**
     * 发送简单邮件
     *
     * @param email
     */
    void sendSimpleMail(cn.meteor.beyondclouds.modules.mail.dto.EmailDTO email);

    /**
     * 发送html邮件
     *
     * @param email
     */
    void sendHtmlMail(cn.meteor.beyondclouds.modules.mail.dto.EmailDTO email);

    /**
     * 发送验证码
     *
     * @param email
     * @param randomVerifyCode
     */
    void sendVerifyCode(java.lang.String email, java.lang.String randomVerifyCode);
}