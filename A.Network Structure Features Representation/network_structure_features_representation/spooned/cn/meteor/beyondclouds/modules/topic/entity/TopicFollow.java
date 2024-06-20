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
 * 话题关注表，记录了用户和话题之间的关注关系
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = false)
@lombok.experimental.Accessors(chain = true)
@io.swagger.annotations.ApiModel(value = "TopicFollow对象", description = "话题关注表，记录了用户和话题之间的关注关系")
public class TopicFollow implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @com.baomidou.mybatisplus.annotation.TableId(value = "tpc_follow_id", type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
    private java.lang.String tpcFollowId;

    @io.swagger.annotations.ApiModelProperty("被关注的话题ID")
    private java.lang.String topicId;

    @io.swagger.annotations.ApiModelProperty("关注话题的用户ID")
    private java.lang.String userId;

    private java.util.Date createTime;

    private java.util.Date updateTime;
}