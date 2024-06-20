package cn.meteor.beyondclouds.modules.project.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 项目评论表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "ProjectComment对象", description = "项目评论表")
public class ProjectComment implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "comment_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer commentId;

    private java.lang.Integer projectId;

    @io.swagger.annotations.ApiModelProperty("评论者ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String userNick;

    @io.swagger.annotations.ApiModelProperty("用户头像")
    private java.lang.String userAvatar;

    @io.swagger.annotations.ApiModelProperty("父级评论 ID")
    private java.lang.Integer parentId;

    @io.swagger.annotations.ApiModelProperty("评论内容")
    private java.lang.String comment;

    @io.swagger.annotations.ApiModelProperty("评论深度")
    private java.lang.Integer depth;

    @io.swagger.annotations.ApiModelProperty("评论路径 ")
    private java.lang.String thread;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}