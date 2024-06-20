package cn.meteor.beyondclouds.modules.question.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 问题扩展表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "QuestionExt对象", description = "问题扩展表")
public class QuestionExt implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.String questionId;

    @io.swagger.annotations.ApiModelProperty("问题详情")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("问题详情html")
    private java.lang.String contentHtml;
}