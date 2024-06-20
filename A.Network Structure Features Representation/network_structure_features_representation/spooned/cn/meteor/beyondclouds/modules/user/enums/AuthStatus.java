package cn.meteor.beyondclouds.modules.user.enums;
import lombok.Getter;
/**
 * 账号类型
 *
 * @author meteor
 */
@lombok.Getter
public enum AuthStatus {

    /**
     * 认证方式被禁用
     */
    DISABLED(-1, "禁用"),
    /**
     * 认证方式待激活
     */
    WAIT_FOR_ACTIVE(0, "待激活"),
    /**
     * 正常
     */
    NORMAL(1, "正常");
    AuthStatus(int status, java.lang.String name) {
        this.status = status;
        this.name = name;
    }

    private int status;

    private java.lang.String name;
}