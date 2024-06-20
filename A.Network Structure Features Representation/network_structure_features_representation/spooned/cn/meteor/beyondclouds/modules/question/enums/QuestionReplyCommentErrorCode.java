package cn.meteor.beyondclouds.modules.question.enums;
/**
 *
 * @author 胡学良
 * @since 2020/2/2
 */
public enum QuestionReplyCommentErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 评论不存在
     */
    COMMENT_NOT_FOUND(5002, "该评论不存在"),
    /**
     * 父评论不存在
     */
    PARENT_COMMENT_NOT_FOUND(5001, "父评论不存在"),
    /**
     * 无权删除评论
     */
    NO_DELETE_PRIVILEGES(5003, "无权删除该评论 ");
    private long code;

    private java.lang.String msg;

    QuestionReplyCommentErrorCode(long code, java.lang.String msg) {
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