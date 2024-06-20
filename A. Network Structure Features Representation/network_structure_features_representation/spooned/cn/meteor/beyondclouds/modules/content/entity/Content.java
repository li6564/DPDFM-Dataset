package cn.meteor.beyondclouds.modules.content.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * CMS-内容表
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-01
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Content对象", description = "CMS-内容表")
public class Content implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "content_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer contentId;

    @io.swagger.annotations.ApiModelProperty("栏目ID")
    private java.lang.Integer channelId;

    @io.swagger.annotations.ApiModelProperty("作者")
    private java.lang.String author;

    @io.swagger.annotations.ApiModelProperty("内容类型-0:幻灯，1:普通文章，3：广告")
    private java.lang.Integer contentType;

    @io.swagger.annotations.ApiModelProperty("内容标题")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String title;

    @io.swagger.annotations.ApiModelProperty("子标题")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String subTitle;

    @io.swagger.annotations.ApiModelProperty("访问链接")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String link;

    @io.swagger.annotations.ApiModelProperty("封面图")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String cover;

    @io.swagger.annotations.ApiModelProperty("图片1")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String pic1;

    @io.swagger.annotations.ApiModelProperty("图片2")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private java.lang.String pic2;

    @io.swagger.annotations.ApiModelProperty("显示优先级")
    private java.lang.Integer priority;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}