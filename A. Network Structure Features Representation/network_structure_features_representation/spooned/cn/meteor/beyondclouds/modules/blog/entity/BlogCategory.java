package cn.meteor.beyondclouds.modules.blog.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 博客类别表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "BlogCategory对象", description = "博客类别表")
public class BlogCategory implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "category_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("父类别ID")
    private java.lang.Integer parentId;

    @io.swagger.annotations.ApiModelProperty("类别名称")
    private java.lang.String category;

    @io.swagger.annotations.ApiModelProperty("类别全名")
    private java.lang.String categoryFull;

    @io.swagger.annotations.ApiModelProperty("类别路径")
    private java.lang.String thread;

    @io.swagger.annotations.ApiModelProperty("类别名称路径")
    private java.lang.String categoryThread;

    private java.lang.Integer priority;

    private java.lang.Boolean leaf;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}