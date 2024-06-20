package cn.meteor.beyondclouds.modules.sms.service;
/**
 * 短信业务类
 *
 * @author meteor
 */
public interface ISmsService {
    /**
     * 发送验证码
     *
     * @param mobile
     * @param randomVerifyCode
     */
    void sendVerifyCode(java.lang.String mobile, java.lang.String randomVerifyCode) throws cn.meteor.beyondclouds.modules.sms.exception.SmsServiceException;
}