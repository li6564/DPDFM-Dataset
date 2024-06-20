package cn.meteor.beyondclouds.modules.app.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 *
 * </p>
 *
 * @author 段启岩
 * @since 2020-03-19
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "App版本", description = "")
public class AppVersion implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @io.swagger.annotations.ApiModelProperty("版本ID")
    @com.baomidou.mybatisplus.annotation.TableId(value = "version_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer versionId;

    @io.swagger.annotations.ApiModelProperty("版本号")
    private java.lang.Long versionCode;

    @io.swagger.annotations.ApiModelProperty("版本名称")
    private java.lang.String versionName;

    @io.swagger.annotations.ApiModelProperty("下载地址")
    private java.lang.String downloadUrl;

    @io.swagger.annotations.ApiModelProperty("版本描述")
    private java.lang.String description;

    @io.swagger.annotations.ApiModelProperty("版本发布日期")
    private java.util.Date createTime;

    private java.util.Date updateTime;
}