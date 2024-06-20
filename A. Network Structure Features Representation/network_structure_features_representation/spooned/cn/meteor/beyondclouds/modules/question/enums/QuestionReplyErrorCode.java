package cn.meteor.beyondclouds.modules.question.enums;
/**
 *
 * @author 胡学良
 * @since 2020/2/1
 */
public enum QuestionReplyErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 回复不存在
     */
    REPLY_NOT_FOUND(4001, "回复不存在"),
    /**
     * 回复已被采纳
     */
    REPLY_ADOPTED(4002, "回复已被采纳"),
    /**
     * 无权删除评论
     */
    NO_DELETE_PRIVILEGES(4003, "无权删除该回复"),
    /**
     * 回复未被采纳
     */
    REPLY_NOT_ADOPTED(4004, "回复未被采纳");
    private long code;

    private java.lang.String msg;

    QuestionReplyErrorCode(long code, java.lang.String msg) {
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