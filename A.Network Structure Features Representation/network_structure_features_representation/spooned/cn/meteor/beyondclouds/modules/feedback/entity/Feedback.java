package cn.meteor.beyondclouds.modules.feedback.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 *
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-14
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Feedback对象", description = "")
public class Feedback implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "feedback_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer feedbackId;

    @io.swagger.annotations.ApiModelProperty("反馈类型，1-反馈，2-举报")
    private java.lang.Integer feedbackType;

    private java.lang.String feedbackReason;

    @io.swagger.annotations.ApiModelProperty("反馈内容")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("链接")
    private java.lang.String link;

    @io.swagger.annotations.ApiModelProperty("手机号")
    private java.lang.String mobile;

    private java.lang.String picture;

    private java.time.LocalDateTime createTime;

    private java.time.LocalDateTime updateTime;
}