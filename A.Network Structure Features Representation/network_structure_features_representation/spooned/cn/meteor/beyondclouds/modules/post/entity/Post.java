package cn.meteor.beyondclouds.modules.post.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 动态表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Post对象", description = "动态表")
public class Post implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "post_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String postId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("动态类型")
    private java.lang.Integer type;

    @io.swagger.annotations.ApiModelProperty("动态内容")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("动态里面的图片")
    private java.lang.String pictures;

    @io.swagger.annotations.ApiModelProperty("动态中的视频")
    private java.lang.String video;

    @io.swagger.annotations.ApiModelProperty("用户头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String userNick;

    @io.swagger.annotations.ApiModelProperty("评论数量")
    private java.lang.Integer commentNumber;

    @io.swagger.annotations.ApiModelProperty("状态")
    private java.lang.Integer status;

    private java.lang.Integer praiseNum;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}