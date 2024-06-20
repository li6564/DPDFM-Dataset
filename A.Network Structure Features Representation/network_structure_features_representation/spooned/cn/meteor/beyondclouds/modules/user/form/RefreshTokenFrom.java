package cn.meteor.beyondclouds.modules.user.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author meteor
 */
@io.swagger.annotations.ApiModel("刷新token表单")
@lombok.Data
public class RefreshTokenFrom {
    @io.swagger.annotations.ApiModelProperty("refresh_token")
    private java.lang.String refreshToken;
}