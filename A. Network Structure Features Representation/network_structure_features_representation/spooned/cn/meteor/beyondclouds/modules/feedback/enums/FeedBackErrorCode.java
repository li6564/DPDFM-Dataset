package cn.meteor.beyondclouds.modules.feedback.enums;
/**
 *
 * @author 胡学良
 * @since 2020/2/14
 */
public enum FeedBackErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {
    ;

    private long code;

    private java.lang.String msg;

    FeedBackErrorCode(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    @java.lang.Override
    public long code() {
        return 0;
    }

    @java.lang.Override
    public java.lang.String msg() {
        return null;
    }
}