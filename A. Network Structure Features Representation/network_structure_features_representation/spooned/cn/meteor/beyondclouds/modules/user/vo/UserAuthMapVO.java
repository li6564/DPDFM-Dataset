package cn.meteor.beyondclouds.modules.user.vo;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class UserAuthMapVO {
    private java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> local;

    private java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> app;
}