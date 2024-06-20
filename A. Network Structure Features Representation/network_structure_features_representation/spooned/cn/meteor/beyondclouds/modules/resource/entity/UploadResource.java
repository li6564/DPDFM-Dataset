package cn.meteor.beyondclouds.modules.resource.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 文件上传资源表
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "UploadResource对象", description = "文件上传资源表")
public class UploadResource implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String resourceId;

    @io.swagger.annotations.ApiModelProperty("用户 ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("资源URL")
    private java.lang.String resourceUrl;

    @io.swagger.annotations.ApiModelProperty("资源类型")
    private java.lang.Integer resourceType;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}