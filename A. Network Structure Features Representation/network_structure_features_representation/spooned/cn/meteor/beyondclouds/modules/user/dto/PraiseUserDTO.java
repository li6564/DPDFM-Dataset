package cn.meteor.beyondclouds.modules.user.dto;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class PraiseUserDTO {
    private java.lang.String userId;

    private java.lang.String nickName;

    private java.lang.String userAvatar;

    private java.lang.String signature;

    private java.util.Date praiseTime;
}