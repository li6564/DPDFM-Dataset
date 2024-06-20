package cn.meteor.beyondclouds.modules.question.enums;
/**
 *
 * @author 胡学良
 * @since 2020/2/1
 */
public enum QuestionTagErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 标签不存在
     */
    TAG_NOT_FOUND(6001, "标签不存在");
    private long code;

    private java.lang.String msg;

    QuestionTagErrorCode(long code, java.lang.String msg) {
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