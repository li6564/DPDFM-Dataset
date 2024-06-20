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
 * 项目表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Project对象", description = "项目表")
public class Project implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "project_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer projectId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("项目名称")
    private java.lang.String projectName;

    @io.swagger.annotations.ApiModelProperty("项目类别ID")
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("项目类别名称")
    private java.lang.String category;

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

    @io.swagger.annotations.ApiModelProperty("项目描述")
    private java.lang.String projectDescription;

    @io.swagger.annotations.ApiModelProperty("封面图")
    private java.lang.String cover;

    @io.swagger.annotations.ApiModelProperty("用户昵称")
    private java.lang.String userNick;

    @io.swagger.annotations.ApiModelProperty("项目评论数量")
    private java.lang.Integer commentNumber;

    @io.swagger.annotations.ApiModelProperty("浏览量")
    private java.lang.Integer viewNumber;

    @io.swagger.annotations.ApiModelProperty("状态")
    private java.lang.Integer status;

    private java.lang.Integer praiseNum;

    private java.lang.Integer priority;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}