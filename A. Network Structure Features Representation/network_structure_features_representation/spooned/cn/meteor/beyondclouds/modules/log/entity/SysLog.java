package cn.meteor.beyondclouds.modules.log.entity;
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
 * @since 2020-02-16
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "SysLog对象", description = "")
public class SysLog implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "log_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String logId;

    @io.swagger.annotations.ApiModelProperty("日志类型")
    private java.lang.Integer logType;

    @io.swagger.annotations.ApiModelProperty("操作对象ID")
    private java.lang.String targetId;

    @io.swagger.annotations.ApiModelProperty("目标类型")
    private java.lang.String targetType;

    @io.swagger.annotations.ApiModelProperty("操作类型")
    private java.lang.String operateType;

    @io.swagger.annotations.ApiModelProperty("操作者ID")
    private java.lang.String operatorId;

    @io.swagger.annotations.ApiModelProperty("操作者IP")
    private java.lang.String operatorIp;

    @io.swagger.annotations.ApiModelProperty("操作时间")
    private java.util.Date createTime;

    public SysLog(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        this.logType = cn.meteor.beyondclouds.modules.log.enums.LogType.DATA_ITEM_CHANGE.getType();
        this.targetId = java.lang.String.valueOf(dataItemChangeMessage.getItemId());
        this.targetType = dataItemChangeMessage.getItemType().name();
        this.operateType = dataItemChangeMessage.getChangeType().name();
        this.operatorId = ((java.lang.String) (dataItemChangeMessage.getSubject().getId()));
        this.operatorIp = dataItemChangeMessage.getSubject().getIpAddress();
    }

    public SysLog(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        this.logType = cn.meteor.beyondclouds.modules.log.enums.LogType.USER_ACTION.getType();
        this.targetId = userActionMessage.getAccount();
        this.targetType = userActionMessage.getAccountType();
        this.operateType = userActionMessage.getAction().name();
        if (userActionMessage.getAction().equals(cn.meteor.beyondclouds.core.queue.message.UserAction.LOGIN_FAILURE)) {
            this.operatorId = cn.meteor.beyondclouds.core.queue.message.UserAction.LOGIN_FAILURE.name();
        } else {
            this.operatorId = ((java.lang.String) (userActionMessage.getSubject().getId()));
        }
        this.operatorIp = userActionMessage.getSubject().getIpAddress();
    }
}