package cn.meteor.beyondclouds.modules.tag.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 标签表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Tag对象", description = "标签表")
public class Tag implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "tag_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String tagId;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @io.swagger.annotations.ApiModelProperty("用户id")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("标签名称")
    private java.lang.String tagName;

    @io.swagger.annotations.ApiModelProperty("标签类型：0博客标签，2-问题标签")
    private java.lang.Integer tagType;

    @io.swagger.annotations.ApiModelProperty("被引用次数")
    private java.lang.Integer referenceCount;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.util.Date createTime;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.util.Date updateTime;
}