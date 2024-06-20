package cn.meteor.beyondclouds.modules.sms.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 短信Api
 *
 * @author meteor
 */
@io.swagger.annotations.Api(tags = "短信Api")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/sms")
public class SmsApi {
    private cn.meteor.beyondclouds.modules.sms.service.ISmsService smsService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setSmsService(cn.meteor.beyondclouds.modules.sms.service.ISmsService smsService) {
        this.smsService = smsService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("发送验证码")
    @org.springframework.web.bind.annotation.GetMapping("/verifyCode")
    public cn.meteor.beyondclouds.core.api.Response sendVerify(@org.springframework.web.bind.annotation.RequestParam("mobile")
    java.lang.String mobile, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(transmitType = cn.meteor.beyondclouds.core.flow.TransmitType.PARAM, paramName = "mobile", type = cn.meteor.beyondclouds.core.flow.ParamType.SMS_CODE)
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        if (!mobile.matches(cn.meteor.beyondclouds.core.constant.RegexPatterns.MOBILE)) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.sms.enums.SmsErrorCode.INVALID_MOBILE);
        }
        if (cn.meteor.beyondclouds.util.AccessInfoUtils.hasFieldInfo(accessInfo)) {
            java.lang.Integer count = accessInfo.getFieldVisitCount();
            if (count > 2) {
                return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.sms.enums.SmsErrorCode.TOO_FREQUENT);
            }
        }
        try {
            smsService.sendVerifyCode(mobile, cn.meteor.beyondclouds.util.VerifyCodeUtils.randomVerifyCode());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.sms.exception.SmsServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}