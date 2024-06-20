package cn.meteor.beyondclouds.modules.user.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author meteor
 */
@io.swagger.annotations.ApiModel("密码认证表单")
@lombok.Data
public class LocalAuthFrom {
    @io.swagger.annotations.ApiModelProperty("账号")
    private java.lang.String account;

    @io.swagger.annotations.ApiModelProperty("密码")
    private java.lang.String password;
}