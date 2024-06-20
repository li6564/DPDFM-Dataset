package cn.meteor.beyondclouds.modules.user.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @program: beyond-clouds
 * @description: 短信认证表单
 * @author: Mr.Zhang
 * @create: 2020-02-03 09:49
 */
@io.swagger.annotations.ApiModel("短信验证表单")
@lombok.Data
public class SmsAuthFrom {
    @io.swagger.annotations.ApiModelProperty("手机号")
    @javax.validation.constraints.NotNull(message = "手机号不能为空")
    @javax.validation.constraints.Pattern(regexp = cn.meteor.beyondclouds.core.constant.RegexPatterns.MOBILE, message = "手机号格式不正确")
    private java.lang.String mobile;

    @io.swagger.annotations.ApiModelProperty("验证码")
    @javax.validation.constraints.NotBlank(message = "验证码不能为空")
    @javax.validation.constraints.NotBlank
    private java.lang.String verifyCode;
}