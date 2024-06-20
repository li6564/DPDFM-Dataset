package cn.meteor.beyondclouds.modules.user.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 用户信息
 *
 * @author 段启岩
 */
@lombok.Data
public class UserInfoVO {
    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String nickName;

    @io.swagger.annotations.ApiModelProperty("用户头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("性别")
    private java.lang.Integer gender;

    @io.swagger.annotations.ApiModelProperty("个性签名")
    private java.lang.String signature;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiModelProperty("手机号")
    private java.lang.String mobile;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiModelProperty("微信号")
    private java.lang.String wxNumber;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiModelProperty("QQ号")
    private java.lang.String qqNumber;
}