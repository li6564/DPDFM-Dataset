package cn.meteor.beyondclouds.modules.user.entity;
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
@io.swagger.annotations.ApiModel(value = "UserRemarks对象", description = "")
public class UserRemarks implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "remark_id", type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private java.lang.Integer remarkId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("被备注的用户的ID")
    private java.lang.String markedId;

    @io.swagger.annotations.ApiModelProperty("备注")
    private java.lang.String remarks;

    private java.time.LocalDateTime createTime;

    private java.time.LocalDateTime updateTime;
}