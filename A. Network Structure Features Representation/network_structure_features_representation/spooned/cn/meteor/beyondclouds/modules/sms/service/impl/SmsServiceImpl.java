package cn.meteor.beyondclouds.modules.sms.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 短信业务实现
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
public class SmsServiceImpl implements cn.meteor.beyondclouds.modules.sms.service.ISmsService {
    private cn.meteor.beyondclouds.common.helper.ISmsHelper smsHelper;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setSmsHelper(cn.meteor.beyondclouds.common.helper.ISmsHelper smsHelper, cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.smsHelper = smsHelper;
        this.redisHelper = redisHelper;
    }

    @java.lang.Override
    public void sendVerifyCode(java.lang.String mobile, java.lang.String randomVerifyCode) throws cn.meteor.beyondclouds.modules.sms.exception.SmsServiceException {
        try {
            // 1. 发送短信
            smsHelper.sendSms(smsHelper.getTemplateCodeMap().get("verifyCode"), java.util.Map.of("code", randomVerifyCode), mobile);
            // 2.存储短信到redis
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile), randomVerifyCode, 5 * 60);
        } catch (java.lang.Exception e) {
            throw new cn.meteor.beyondclouds.modules.sms.exception.SmsServiceException(cn.meteor.beyondclouds.modules.sms.enums.SmsErrorCode.SEND_FAILURE);
        }
    }
}