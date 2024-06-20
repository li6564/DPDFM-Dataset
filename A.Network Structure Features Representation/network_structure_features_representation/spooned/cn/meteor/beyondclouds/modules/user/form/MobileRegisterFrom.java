package cn.meteor.beyondclouds.modules.user.form;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class MobileRegisterFrom {
    @javax.validation.constraints.NotEmpty(message = "手机不能为空")
    @javax.validation.constraints.NotNull(message = "手机号不能为空")
    @javax.validation.constraints.Pattern(regexp = cn.meteor.beyondclouds.core.constant.RegexPatterns.MOBILE, message = "手机号格式不正确")
    private java.lang.String mobile;

    @javax.validation.constraints.NotEmpty(message = "密码不能为空")
    private java.lang.String password;

    @javax.validation.constraints.NotEmpty(message = "验证码不能为空")
    private java.lang.String verifyCode;
}