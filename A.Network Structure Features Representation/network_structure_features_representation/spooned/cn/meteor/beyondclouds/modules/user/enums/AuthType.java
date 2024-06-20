package cn.meteor.beyondclouds.modules.user.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum AuthType {

    /* 认证方式 */
    APP("app"),
    LOCAL("local");
    private java.lang.String type;

    AuthType(java.lang.String type) {
        this.type = type;
    }
}