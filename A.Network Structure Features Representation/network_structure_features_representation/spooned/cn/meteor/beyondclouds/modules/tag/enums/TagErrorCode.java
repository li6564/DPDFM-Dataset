package cn.meteor.beyondclouds.modules.tag.enums;
/**
 * 标签相关错误码
 *
 * @author 胡明森
 */
public enum TagErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 标签类型存在
     */
    TAGTYPE_EXISTS(3001, "该标签存在"),
    TAGTYPE_NOT_EXISTS(3002, "该标签类型不存在");
    TagErrorCode(long code, java.lang.String msg) {
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