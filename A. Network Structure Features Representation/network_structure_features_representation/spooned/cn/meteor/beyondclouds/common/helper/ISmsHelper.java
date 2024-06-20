package cn.meteor.beyondclouds.common.helper;
/**
 * 短信辅助类
 *
 * @author meteor
 */
public interface ISmsHelper {
    /**
     * 发送短信给目标手机号
     *
     * @param templateCode
     * 		短信模版code
     * @param templateParam
     * 		短信模版参数
     * @param mobiles
     * 		目标手机号
     */
    void sendSms(java.lang.String templateCode, java.util.Map<java.lang.String, java.lang.String> templateParam, java.lang.String... mobiles) throws cn.meteor.beyondclouds.common.exception.SmsException;

    /**
     * 获取所有的模版code
     *
     * @return  */
    java.util.Map<java.lang.String, java.lang.String> getTemplateCodeMap();
}