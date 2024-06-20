package cn.meteor.beyondclouds.modules.project.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 段启岩
 */
@io.swagger.annotations.ApiModel(value = "ProjectForm", description = "项目表单")
@lombok.Data
public class ProjectForm {
    private static final long serialVersionUID = 1L;

    @javax.validation.constraints.NotEmpty(message = "项目名称不能为空")
    @io.swagger.annotations.ApiModelProperty("项目名称")
    private java.lang.String projectName;

    @javax.validation.constraints.NotNull(message = "项目类别不能为空")
    @io.swagger.annotations.ApiModelProperty("项目类别ID")
    private java.lang.Integer categoryId;

    @javax.validation.constraints.NotEmpty(message = "项目源码链接不能为空")
    @io.swagger.annotations.ApiModelProperty("项目源码链接")
    private java.lang.String sourceLink;

    @io.swagger.annotations.ApiModelProperty("项目主页链接")
    private java.lang.String homeLink;

    @io.swagger.annotations.ApiModelProperty("项目文档链接")
    private java.lang.String docLink;

    @io.swagger.annotations.ApiModelProperty("协议")
    private java.lang.String license;

    @io.swagger.annotations.ApiModelProperty("项目开发语言")
    private java.lang.String devLang;

    @io.swagger.annotations.ApiModelProperty("项目作者")
    private java.lang.String author;

    @javax.validation.constraints.NotEmpty(message = "项目描述不能为空")
    @io.swagger.annotations.ApiModelProperty("项目描述")
    private java.lang.String projectDescription;

    @io.swagger.annotations.ApiModelProperty("封面图")
    private java.lang.String cover;

    @io.swagger.annotations.ApiModelProperty("项目详情")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("项目详情html")
    private java.lang.String contentHtml;
}