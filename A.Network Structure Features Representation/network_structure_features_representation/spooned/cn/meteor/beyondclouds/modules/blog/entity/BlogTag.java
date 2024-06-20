package cn.meteor.beyondclouds.modules.blog.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 博客标签表，用来记录博客里面引用了哪些标签
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "BlogTag对象", description = "博客标签表，用来记录博客里面引用了哪些标签")
public class BlogTag implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @io.swagger.annotations.ApiModelProperty("博客主键")
    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.String blogId;

    @io.swagger.annotations.ApiModelProperty("标签主键")
    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.String tagId;

    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 更新时间
     */
    private java.util.Date updateTime;
}