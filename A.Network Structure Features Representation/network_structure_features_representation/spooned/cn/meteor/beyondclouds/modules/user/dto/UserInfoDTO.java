package cn.meteor.beyondclouds.modules.user.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class UserInfoDTO {
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String nickName;

    @io.swagger.annotations.ApiModelProperty("用户头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("性别")
    private java.lang.Integer gender;

    @io.swagger.annotations.ApiModelProperty("个性签名")
    private java.lang.String signature;

    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @io.swagger.annotations.ApiModelProperty("生日")
    private java.util.Date birthday;

    @io.swagger.annotations.ApiModelProperty("手机号")
    private java.lang.String mobile;

    @io.swagger.annotations.ApiModelProperty("邮箱")
    private java.lang.String email;

    @io.swagger.annotations.ApiModelProperty("微信号")
    private java.lang.String wxNumber;

    @io.swagger.annotations.ApiModelProperty("QQ号")
    private java.lang.String qqNumber;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.Boolean nickCanModify;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    private cn.meteor.beyondclouds.modules.user.entity.UserStatistics statistics;
}