package cn.meteor.beyondclouds.modules.topic.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 话题表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Topic对象", description = "话题表")
public class Topic implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "topic_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String topicId;

    @io.swagger.annotations.ApiModelProperty("话题创建者ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("话题名称")
    private java.lang.String topicName;

    @io.swagger.annotations.ApiModelProperty("话题图标")
    private java.lang.String topicIcon;

    @io.swagger.annotations.ApiModelProperty("封面图")
    private java.lang.String cover;

    @io.swagger.annotations.ApiModelProperty("话题描述")
    private java.lang.String topicDescrption;

    @io.swagger.annotations.ApiModelProperty("被引用次数")
    private java.lang.Integer referenceCount;

    private java.lang.Integer status;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}