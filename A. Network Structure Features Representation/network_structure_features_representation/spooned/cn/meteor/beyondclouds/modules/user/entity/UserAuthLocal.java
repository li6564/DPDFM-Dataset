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
 * 用户本地认证表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "UserAuthLocal对象", description = "用户本地认证表")
public class UserAuthLocal implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "ua_local_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String uaLocalId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("账号")
    private java.lang.String account;

    /**
     * 账号类型
     * 1-手机号
     * 2-邮箱账号
     */
    @io.swagger.annotations.ApiModelProperty("账号类型")
    private java.lang.Integer accountType;

    @io.swagger.annotations.ApiModelProperty("密码")
    private java.lang.String password;

    /**
     * 状态：
     * -1-被禁用
     * 0-待激活
     * 1-正常
     */
    @io.swagger.annotations.ApiModelProperty("状态")
    private java.lang.Integer status;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}