package cn.meteor.beyondclouds.modules.project.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 项目扩展表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "ProjectExt对象", description = "项目扩展表")
public class ProjectExt implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId
    private java.lang.Integer projectId;

    @io.swagger.annotations.ApiModelProperty("项目详情")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("项目详情html")
    private java.lang.String contentHtml;
}