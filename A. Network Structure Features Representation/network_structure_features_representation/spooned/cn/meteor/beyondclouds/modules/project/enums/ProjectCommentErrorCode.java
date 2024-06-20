package cn.meteor.beyondclouds.modules.project.enums;
/**
 *
 * @author 段启岩
 */
public enum ProjectCommentErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    PARENT_COMMENT_NOT_FOUND(100001, "父评论不存在"),
    COMMENT_NOT_FOUND(100002, "评论不存在"),
    NO_DELETE_PRIVILEGES(100003, "无权删除该评论");
    private long code;

    private java.lang.String msg;

    ProjectCommentErrorCode(long code, java.lang.String msg) {
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