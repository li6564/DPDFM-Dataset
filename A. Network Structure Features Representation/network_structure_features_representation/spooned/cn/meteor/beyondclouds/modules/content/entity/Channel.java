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
 * CMS-栏目表
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-01
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Channel对象", description = "CMS-栏目表")
public class Channel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "channel_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer channelId;

    @io.swagger.annotations.ApiModelProperty("父级栏目ID")
    private java.lang.Integer parentId;

    @io.swagger.annotations.ApiModelProperty("栏目名称")
    private java.lang.String channelName;

    @io.swagger.annotations.ApiModelProperty("栏目路径")
    private java.lang.String thread;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}