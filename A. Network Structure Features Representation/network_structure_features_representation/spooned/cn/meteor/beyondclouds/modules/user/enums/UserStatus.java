package cn.meteor.beyondclouds.modules.user.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum UserStatus {

    NORMAL(0),
    DISABLED(-1);
    private java.lang.Integer status;

    UserStatus(java.lang.Integer status) {
        this.status = status;
    }
}