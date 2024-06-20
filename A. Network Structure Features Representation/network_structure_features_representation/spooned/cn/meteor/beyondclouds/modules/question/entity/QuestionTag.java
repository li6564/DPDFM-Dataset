package cn.meteor.beyondclouds.modules.question.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 问题标签表，用来记录问题里面引用的标签
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "QuestionTag对象", description = "问题标签表，用来记录问题里面引用的标签")
public class QuestionTag implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @io.swagger.annotations.ApiModelProperty("标签ID")
    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.String tagId;

    @io.swagger.annotations.ApiModelProperty("问题ID")
    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.String questionId;

    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    private java.util.Date updateTime;
}