package cn.meteor.beyondclouds.modules.topic.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 话题引用表，记录了其他模块（项目，博客，问答等）对话题的引用
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "TopicReference对象", description = "话题引用表，记录了其他模块（项目，博客，问答等）对话题的引用")
public class TopicReference implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "reference_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String referenceId;

    @io.swagger.annotations.ApiModelProperty("话题ID")
    private java.lang.String topicId;

    @io.swagger.annotations.ApiModelProperty("用户ID")
    private java.lang.String userId;

    @io.swagger.annotations.ApiModelProperty("引用者（博客，项目动态等）ID")
    private java.lang.String referencerId;

    @io.swagger.annotations.ApiModelProperty("引用类型：0-博客，1-项目，2-问答，3-动态")
    private java.lang.Integer referencerType;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}