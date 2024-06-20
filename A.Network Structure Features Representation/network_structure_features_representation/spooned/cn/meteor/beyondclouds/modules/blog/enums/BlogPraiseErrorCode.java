package cn.meteor.beyondclouds.modules.blog.enums;
/**
 *
 * @author gaoTong
 * @date 2020/2/20 10:59
 */
public enum BlogPraiseErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    BLOG_PRAISE_EXIST(8008, "您已经点过赞了"),
    NO_PRAISE_FOUND(8009, "您没有点过赞");
    private long code;

    private java.lang.String msg;

    BlogPraiseErrorCode(long code, java.lang.String msg) {
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