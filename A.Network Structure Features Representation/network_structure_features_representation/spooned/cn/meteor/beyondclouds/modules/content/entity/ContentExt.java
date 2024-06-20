package cn.meteor.beyondclouds.modules.content.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * CMS-内容扩展表
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-01
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "ContentExt对象", description = "CMS-内容扩展表")
public class ContentExt implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.Integer contentId;

    @io.swagger.annotations.ApiModelProperty("内容")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String content;
}