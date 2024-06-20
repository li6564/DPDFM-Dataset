package cn.meteor.beyondclouds.common.helper.impl;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
/**
 *
 * @author 段启岩
 */
@org.springframework.stereotype.Component
public class SmsHelperImpl implements cn.meteor.beyondclouds.common.helper.ISmsHelper {
    private static final java.lang.String SMS_RESPONSE_DATA_KEY = "Code";

    private static final java.lang.String SMS_RESPONSE_DATA_VALUE_OK = "OK";

    private static final java.lang.String SMS_REQUEST_PARAM_NAME_REGION_ID = "RegionId";

    private static final java.lang.String SMS_REQUEST_PARAM_NAME_SIGN_NAME = "SignName";

    private static final java.lang.String SMS_REQUEST_PARAM_NAME_PHONE_NUMBERS = "PhoneNumbers";

    private static final java.lang.String SMS_REQUEST_PARAM_NAME_TEMPLATE_CODE = "TemplateCode";

    private static final java.lang.String SMS_REQUEST_PARAM_NAME_TEMPLATE_PARAM = "TemplateParam";

    private com.aliyuncs.IAcsClient iAcsClient;

    private cn.meteor.beyondclouds.config.properties.AliyunProperties aliyunProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public void setiAcsClient(com.aliyuncs.IAcsClient iAcsClient, cn.meteor.beyondclouds.config.properties.AliyunProperties aliyunProperties) {
        this.iAcsClient = iAcsClient;
        this.aliyunProperties = aliyunProperties;
    }

    @java.lang.Override
    public void sendSms(java.lang.String templateCode, java.util.Map<java.lang.String, java.lang.String> templateParam, java.lang.String... mobiles) throws cn.meteor.beyondclouds.common.exception.SmsException {
        org.springframework.util.Assert.notEmpty(mobiles, "请至少传入一个手机号！");
        // 将数组形式的手机号列表转换为用逗号分割的手机号字符串
        java.lang.String mobilesStr = java.util.Arrays.stream(mobiles).map(mobile -> mobile + ",").collect(java.util.stream.Collectors.joining());
        mobilesStr = mobilesStr.substring(0, mobilesStr.length() - 1);
        // 构造发短信的请求
        cn.meteor.beyondclouds.config.properties.SmsProperties smsProperties = aliyunProperties.getSms();
        com.aliyuncs.CommonRequest request = new com.aliyuncs.CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(smsProperties.getDomain());
        request.setVersion(smsProperties.getVersion());
        request.setAction(smsProperties.getAction());
        request.putQueryParameter(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_REQUEST_PARAM_NAME_REGION_ID, smsProperties.getRegionId());
        request.putQueryParameter(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_REQUEST_PARAM_NAME_SIGN_NAME, smsProperties.getSignName());
        request.putQueryParameter(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_REQUEST_PARAM_NAME_PHONE_NUMBERS, mobilesStr);
        request.putQueryParameter(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_REQUEST_PARAM_NAME_TEMPLATE_CODE, templateCode);
        request.putQueryParameter(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_REQUEST_PARAM_NAME_TEMPLATE_PARAM, cn.meteor.beyondclouds.util.JsonUtils.toJson(templateParam));
        try {
            // 发送短信
            com.aliyuncs.CommonResponse response = iAcsClient.getCommonResponse(request);
            java.util.Map data = cn.meteor.beyondclouds.util.JsonUtils.toBean(response.getData(), java.util.Map.class);
            if (!data.get(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_RESPONSE_DATA_KEY).equals(cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl.SMS_RESPONSE_DATA_VALUE_OK)) {
                throw new cn.meteor.beyondclouds.common.exception.SmsException("短信发送失败");
            }
        } catch (java.lang.Exception e) {
            throw new cn.meteor.beyondclouds.common.exception.SmsException(e.getMessage(), e);
        }
    }

    public static void main(java.lang.String[] args) throws cn.meteor.beyondclouds.common.exception.SmsException {
        new cn.meteor.beyondclouds.common.helper.impl.SmsHelperImpl().sendSms("", java.util.Map.of());
    }

    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.String> getTemplateCodeMap() {
        return aliyunProperties.getSms().getTemplateCodeMap();
    }
}