package cn.meteor.beyondclouds.modules.topic.enums;
/**
 * 话题相关错误码
 *
 * @author 胡明森
 */
public enum TopicErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 话题已存在
     */
    TOPIC_EXISTS(3001, "该话题已存在"),
    TOPIC_NOT_EXISTS(3002, "该话题不存在"),
    AlREADY_FOLLOWED(3003, "已关注过该话题"),
    NOT_FOLLOWED(3004, "未关注过该话题"),
    TOPIC_FORMAT_ERROR(3005, "话题格式错误");
    TopicErrorCode(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    private long code;

    private java.lang.String msg;

    @java.lang.Override
    public long code() {
        return code;
    }

    @java.lang.Override
    public java.lang.String msg() {
        return msg;
    }
}