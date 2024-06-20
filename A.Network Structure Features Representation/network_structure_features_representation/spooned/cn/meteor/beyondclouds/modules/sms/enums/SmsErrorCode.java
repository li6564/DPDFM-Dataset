package cn.meteor.beyondclouds.modules.sms.enums;
/**
 *
 * @author meteor
 */
public enum SmsErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    SERVER_ERROR(2001, "发送短信时阿里的服务器出现了异常"),
    CLIENT_ERROR(2002, "发送短信时自己的服务器出现了异常"),
    SEND_FAILURE(2004, "短信发送失败"),
    INVALID_MOBILE(2005, "手机号格式错误"),
    TOO_FREQUENT(2006, "短信发生太频繁");
    private long code;

    private java.lang.String msg;

    SmsErrorCode(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    @java.lang.Override
    public long code() {
        return code;
    }

    @java.lang.Override
    public java.lang.String msg() {
        return msg;
    }
}