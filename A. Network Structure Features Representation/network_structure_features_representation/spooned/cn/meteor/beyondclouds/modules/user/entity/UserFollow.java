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
 * 用户关注表，记录了用户和用户之间的关注和被关注的关系
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "UserFollow对象", description = "用户关注表，记录了用户和用户之间的关注和被关注的关系")
public class UserFollow implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "user_follow_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String userFollowId;

    @io.swagger.annotations.ApiModelProperty("关注者ID")
    private java.lang.String followerId;

    @io.swagger.annotations.ApiModelProperty("被关注者ID")
    private java.lang.String followedId;

    @io.swagger.annotations.ApiModelProperty("关注者昵称")
    private java.lang.String followerNick;

    @io.swagger.annotations.ApiModelProperty("关注者头像")
    private java.lang.String followerAvatar;

    @io.swagger.annotations.ApiModelProperty("被关注者昵称")
    private java.lang.String followedNick;

    @io.swagger.annotations.ApiModelProperty("被关注者头像")
    private java.lang.String followedAvatar;

    @io.swagger.annotations.ApiModelProperty("关注状态：0：正常，-1：取消关注")
    private java.lang.Integer followStatus;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}