package cn.meteor.beyondclouds.modules.user.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 用户信息
 *
 * @author 段启岩
 */
@lombok.Data
public class UserInfoWithFollowedVO {
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String nickName;

    @io.swagger.annotations.ApiModelProperty("用户头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("性别")
    private java.lang.Integer gender;

    @io.swagger.annotations.ApiModelProperty("个性签名")
    private java.lang.String signature;

    @io.swagger.annotations.ApiModelProperty("手机号")
    private java.lang.String mobile;

    @io.swagger.annotations.ApiModelProperty("邮箱")
    private java.lang.String email;

    @io.swagger.annotations.ApiModelProperty("微信号")
    private java.lang.String wxNumber;

    @io.swagger.annotations.ApiModelProperty("QQ号")
    private java.lang.String qqNumber;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    /**
     * 是否关注该用户
     */
    private java.lang.Boolean followedUser;

    private cn.meteor.beyondclouds.modules.user.entity.UserStatistics statistics;
}