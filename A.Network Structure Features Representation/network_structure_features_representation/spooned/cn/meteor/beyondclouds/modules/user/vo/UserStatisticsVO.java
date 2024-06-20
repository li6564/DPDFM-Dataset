package cn.meteor.beyondclouds.modules.user.vo;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class UserStatisticsVO {
    private java.lang.String userId;

    private java.lang.String userNick;

    private java.lang.String userAvatar;

    private cn.meteor.beyondclouds.modules.user.entity.UserStatistics statistics;
}