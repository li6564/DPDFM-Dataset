package cn.meteor.beyondclouds.modules.user.enums;
/**
 * 认证相关错误码
 *
 * @author meteor
 */
public enum AuthenticationErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 用户不存在//
     */
    USER_NOT_FOUND(1001, "不存在该用户"),
    PASSWORD_NOT_MATCHED(1002, "密码错误"),
    QQ_AUTH_ERROR(1004, "QQ认证失败"),
    USER_AUTH_DISABLED(1005, "该账号已被禁用"),
    ACCOUNT_NOT_ACTIVE(1006, "请先激活该账号");
    AuthenticationErrorCode(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    private long code;

    private java.lang.String msg;

    @java.lang.Override
    public long code() {
        return code;
    }

    @java.lang.Override
    public java.lang.String msg() {
        return msg;
    }
}