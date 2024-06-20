package cn.meteor.beyondclouds.modules.topic.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 胡明森
 * @since 2020/2/8
 */
@lombok.Data
public class TopicDTO {
    private java.lang.String topicId;

    private java.lang.String userId;

    private java.lang.String topicName;

    private java.lang.String topicIcon;

    private java.lang.String cover;

    private java.lang.Double degree;

    private java.lang.String topicDescrption;

    private java.lang.Integer referenceCount;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    private java.lang.Boolean followedTopic;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics;
}