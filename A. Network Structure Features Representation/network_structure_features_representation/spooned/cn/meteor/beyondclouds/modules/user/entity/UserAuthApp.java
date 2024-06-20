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
 * 用户第三方认证表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "UserAuthApp对象", description = "用户第三方认证表")
public class UserAuthApp implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "ua_app_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String uaAppId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("第三方app类型")
    private java.lang.Integer appType;

    @io.swagger.annotations.ApiModelProperty("第三方app里面用户的唯一标识")
    private java.lang.String appUserId;

    @io.swagger.annotations.ApiModelProperty("第三方app里面用户的token")
    private java.lang.String appAccessToken;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}