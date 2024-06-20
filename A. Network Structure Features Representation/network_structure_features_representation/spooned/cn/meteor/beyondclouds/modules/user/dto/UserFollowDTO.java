package cn.meteor.beyondclouds.modules.user.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class UserFollowDTO {
    private java.lang.String userId;

    private java.lang.String userNick;

    private java.lang.String userAvatar;

    private java.lang.Boolean followedUser;

    private cn.meteor.beyondclouds.modules.user.entity.UserStatistics statistics;
}