package cn.meteor.beyondclouds.modules.user.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 认证结果
 *
 * @author meteor
 */
@lombok.Data
@io.swagger.annotations.ApiModel("认证结果")
public class AuthenticationResultDTO {
    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("token")
    private java.lang.String accessToken;

    @io.swagger.annotations.ApiModelProperty("refreshToken")
    private java.lang.String refreshToken;
}