package cn.meteor.beyondclouds.modules.question.entity;
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
 * 问题表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Question对象", description = "问题表")
public class Question implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "question_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String questionId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String userNick;

    @io.swagger.annotations.ApiModelProperty("问题类别ID")
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("问题类别名称")
    private java.lang.String category;

    @io.swagger.annotations.ApiModelProperty("问题标题")
    private java.lang.String questionTitle;

    @io.swagger.annotations.ApiModelProperty("问题摘要")
    private java.lang.String questionAbstract;

    @io.swagger.annotations.ApiModelProperty("问题回复数")
    private java.lang.Integer replyNumber;

    @io.swagger.annotations.ApiModelProperty("问题是否已被解决")
    private java.lang.Boolean solved;

    @io.swagger.annotations.ApiModelProperty("浏览量")
    private java.lang.Integer viewNumber;

    private java.lang.Integer praiseNum;

    private java.lang.Integer priority;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    @io.swagger.annotations.ApiModelProperty("状态")
    private java.lang.Integer status;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> tags;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topics;
}