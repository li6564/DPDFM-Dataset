package cn.meteor.beyondclouds.modules.question.enums;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
public enum QuestionErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 问题没有以？结尾
     */
    QUESTION_END_ERROR(3001, "问题没有以？结尾"),
    /**
     * 问题的类别ID错误
     */
    INCORRECT_CATEGORY(3002, "问题类别错误"),
    /**
     * 找不到该问题
     */
    QUESTION_NOT_FOUND(3003, "找不到该问题"),
    ALREADY_PRAISED(3004, "已经点过赞了"),
    NO_PRAISE(3005, "没有点过赞"),
    QUESTION_REPLY_NOT_FOUND(3006, "没有找到该回复");
    private long code;

    private java.lang.String msg;

    QuestionErrorCode(long code, java.lang.String msg) {
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