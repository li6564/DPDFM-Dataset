package cn.meteor.beyondclouds.modules.mail.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author meteor
 */
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/mail")
@io.swagger.annotations.Api(tags = "邮箱API")
public class MailApi {
    private cn.meteor.beyondclouds.modules.mail.service.IMailService mailService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setMailService(cn.meteor.beyondclouds.modules.mail.service.IMailService mailService) {
        this.mailService = mailService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("发送验证码")
    @org.springframework.web.bind.annotation.GetMapping("/verifyCode")
    public cn.meteor.beyondclouds.core.api.Response sendVerify(@org.springframework.web.bind.annotation.RequestParam("email")
    java.lang.String email) {
        try {
            mailService.sendVerifyCode(email, cn.meteor.beyondclouds.util.VerifyCodeUtils.randomVerifyCode());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.common.enums.ErrorCode.OPERATION_FAILED);
        }
    }
}