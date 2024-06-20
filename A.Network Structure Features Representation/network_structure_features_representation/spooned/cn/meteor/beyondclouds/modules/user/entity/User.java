package cn.meteor.beyondclouds.modules.user.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
// 调用set和get方法后返回对象
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "User对象", description = "用户信息表")
public class User implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "user_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String nickName;

    @io.swagger.annotations.ApiModelProperty("用户头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("性别")
    private java.lang.Integer gender;

    @io.swagger.annotations.ApiModelProperty("个性签名")
    private java.lang.String signature;

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

    @io.swagger.annotations.ApiModelProperty("本月开始修改昵称的时间")
    private java.util.Date nickModifyStartTime;

    @io.swagger.annotations.ApiModelProperty("本阶段昵称修改次数")
    private java.lang.Integer nickModifyCount;

    @io.swagger.annotations.ApiModelProperty("用户状态")
    private java.lang.Integer status;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}