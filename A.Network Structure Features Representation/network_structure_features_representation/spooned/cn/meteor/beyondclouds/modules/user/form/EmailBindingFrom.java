package cn.meteor.beyondclouds.modules.user.form;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class EmailBindingFrom {
    @javax.validation.constraints.Email(message = "邮箱格式不准确")
    @javax.validation.constraints.NotNull(message = "邮箱不能为空")
    private java.lang.String email;

    @javax.validation.constraints.NotEmpty(message = "验证码")
    private java.lang.String verifyCode;
}