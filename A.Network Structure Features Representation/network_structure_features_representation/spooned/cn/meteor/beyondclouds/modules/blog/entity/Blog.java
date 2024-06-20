package cn.meteor.beyondclouds.modules.blog.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 博客表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Blog对象", description = "博客表")
public class Blog implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "blog_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String blogId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("所属类别ID")
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("类别名称")
    private java.lang.String category;

    @io.swagger.annotations.ApiModelProperty("标题")
    private java.lang.String blogTitle;

    @io.swagger.annotations.ApiModelProperty("摘要")
    private java.lang.String blogAbstract;

    @io.swagger.annotations.ApiModelProperty("封面图")
    private java.lang.String cover;

    @io.swagger.annotations.ApiModelProperty("原文链接")
    private java.lang.String originLink;

    @io.swagger.annotations.ApiModelProperty("查看权限")
    private java.lang.Integer viewPrivileges;

    @io.swagger.annotations.ApiModelProperty("是否允许评论")
    private java.lang.Boolean allowComment;

    @io.swagger.annotations.ApiModelProperty("是否允许转发")
    private java.lang.Boolean allowForward;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> tags;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topics;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String userNick;

    @io.swagger.annotations.ApiModelProperty("博客评论数量")
    private java.lang.Integer commentNumber;

    @io.swagger.annotations.ApiModelProperty("浏览量")
    private java.lang.Integer viewNumber;

    @io.swagger.annotations.ApiModelProperty("状态")
    private java.lang.Integer status;

    private java.lang.Integer praiseNum;

    private java.lang.Integer priority;

    private java.lang.Integer categoryPriority;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}