package cn.meteor.beyondclouds.modules.blog.enums;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:20
 */
public enum BlogErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    USERID_AUTH_ERROR(8001, "不是当前博客的拥有者"),
    BLOG_NOT_FOUND(8002, "当前博客已经不存在"),
    NO_VIEW_PRIVILEGES(8003, "没有查看权限"),
    USER_BLOG_NOT_FOUND(8004, "用户未发布过此博客");
    private long code;

    private java.lang.String msg;

    BlogErrorCode(long code, java.lang.String msg) {
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