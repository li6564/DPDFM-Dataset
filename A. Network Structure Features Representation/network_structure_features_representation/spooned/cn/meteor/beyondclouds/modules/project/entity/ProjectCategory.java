package cn.meteor.beyondclouds.modules.project.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 项目类别表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "ProjectCategory对象", description = "项目类别表")
public class ProjectCategory implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "category_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("父类别ID")
    private java.lang.Integer parentId;

    @io.swagger.annotations.ApiModelProperty("类别名称")
    private java.lang.String category;

    @io.swagger.annotations.ApiModelProperty("类别路径")
    private java.lang.String thread;

    @io.swagger.annotations.ApiModelProperty("类别名称路径")
    private java.lang.String categoryThread;

    private java.lang.Integer priority;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}