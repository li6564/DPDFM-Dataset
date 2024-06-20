package cn.meteor.beyondclouds.modules.blog.enums;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:21
 */
public enum BlogCommentErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    COMMENT_NOT_FOUND(8003, "该评论不存在"),
    NO_DELETE_PRIVILEGES(8004, "没有权限删除评论"),
    PARENT_COMMENT_NOT_FOUND(8006, "父评论不存在"),
    NO_COMMENT_PRIVILEGES(8007, "没有评论权限");
    private long code;

    private java.lang.String msg;

    BlogCommentErrorCode(long code, java.lang.String msg) {
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