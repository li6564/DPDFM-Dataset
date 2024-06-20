package cn.meteor.beyondclouds.modules.message.entity;
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
 * @since 2020-02-12
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "Message对象", description = "")
public class Message implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @io.swagger.annotations.ApiModelProperty("消息ID")
    @com.baomidou.mybatisplus.annotation.TableId(value = "message_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String messageId;

    @io.swagger.annotations.ApiModelProperty("消息发送者ID")
    private java.lang.String fromId;

    @io.swagger.annotations.ApiModelProperty("消息发送者昵称")
    private java.lang.String fromName;

    @io.swagger.annotations.ApiModelProperty("消息发送者头像")
    private java.lang.String fromAvatar;

    @io.swagger.annotations.ApiModelProperty("消息接收者ID")
    private java.lang.String toId;

    @io.swagger.annotations.ApiModelProperty("消息类型")
    private java.lang.Integer msgType;

    @io.swagger.annotations.ApiModelProperty("消息内容")
    private java.lang.String msgContent;

    @io.swagger.annotations.ApiModelProperty("消息状态-0:未读，1:已读")
    private java.lang.Integer status;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}