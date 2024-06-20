package cn.meteor.beyondclouds.modules.post.entity;
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
 * @since 2020-02-20
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "PostPraise对象", description = "")
public class PostPraise implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "praise_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String praiseId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("目标ID")
    private java.lang.String targetId;

    @io.swagger.annotations.ApiModelProperty("目标类型-1:动态，2：动态评论")
    private java.lang.Integer targetType;

    private java.time.LocalDateTime createTime;

    private java.time.LocalDateTime updateTime;
}