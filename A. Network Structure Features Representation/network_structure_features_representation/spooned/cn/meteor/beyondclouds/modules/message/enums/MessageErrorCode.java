package cn.meteor.beyondclouds.modules.message.enums;
/**
 *
 * @program: beyond-clouds
 * @description:  * @author: Mr.Zhang
 * @create: 2020-02-12 10:28
 */
public enum MessageErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    MESSAGE_NOT_EXIST(1001, "该消息不存在");
    MessageErrorCode(long code, java.lang.String msg) {
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