package cn.meteor.beyondclouds.modules.user.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
/**
 *
 * @program: beyond-clouds
 * @description: 用户信息表
 * @author: Mr.Zhang
 * @create: 2020-01-31 11:41
 */
@lombok.Data
@io.swagger.annotations.ApiModel("用户信息表")
public class UserBaseInfoFrom {
    @io.swagger.annotations.ApiModelProperty("昵称")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的昵称")
    private java.lang.String nickName;

    @io.swagger.annotations.ApiModelProperty("头像")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请选择有效的头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("性别")
    @javax.validation.constraints.Max(value = 2, message = "请输入有效性别")
    @javax.validation.constraints.Min(value = 0, message = "请输入有效性别")
    private java.lang.Integer gender;

    @io.swagger.annotations.ApiModelProperty("签名")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的签名")
    private java.lang.String signature;

    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date birthday;

    @io.swagger.annotations.ApiModelProperty("手机号")
    @javax.validation.constraints.Pattern(regexp = cn.meteor.beyondclouds.core.constant.RegexPatterns.MOBILE, message = "手机号格式不正确")
    private java.lang.String mobile;

    @io.swagger.annotations.ApiModelProperty("微信号")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的微信号")
    private java.lang.String wxNumber;

    @io.swagger.annotations.ApiModelProperty("qq号")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的qq号")
    private java.lang.String qqNumber;
}