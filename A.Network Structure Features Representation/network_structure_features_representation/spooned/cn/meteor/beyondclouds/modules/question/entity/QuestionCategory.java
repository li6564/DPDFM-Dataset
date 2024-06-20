package cn.meteor.beyondclouds.modules.question.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 问题类别表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "QuestionCategory对象", description = "问题类别表")
public class QuestionCategory implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "category_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("类别名称")
    private java.lang.String category;

    private java.lang.Integer priority;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}