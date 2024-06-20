package cn.meteor.beyondclouds.modules.post.enums;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 14:43
 */
public enum PostCommentErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    COMMENT_NOT_FOUND(10003, "该评论不存在"),
    NO_DELETE_PRIVILEGES(10004, "没有权限删除评论"),
    PARENT_COMMENT_NOT_FOUND(10006, "父评论不存在"),
    SUN_COMMENT_NOT_FOUND(1007, "子级评论不存在");
    private long code;

    private java.lang.String msg;

    PostCommentErrorCode(long code, java.lang.String msg) {
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