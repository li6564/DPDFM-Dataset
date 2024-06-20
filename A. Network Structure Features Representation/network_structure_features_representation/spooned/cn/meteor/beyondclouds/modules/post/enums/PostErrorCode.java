package cn.meteor.beyondclouds.modules.post.enums;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 14:43
 */
public enum PostErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    NOT_APPEAR_SAME_TIME(10001, "视频和图片不能同时出现"),
    POST_MUST_NOT_NULL(10002, "动态无内容"),
    POST_NOT_FOUND(10003, "该动态不存在"),
    USER_POST_NOT_FOUND(10004, "用户未发布过此动态"),
    TYPE_ERROR(10005, "动态类型错误，类型只能为0，1，2"),
    POST_PRAISE_EXIST(10008, "已点赞"),
    POST_PRAISE_NOTEXIST(10009, "未点赞"),
    POST_PRAISE_TYPE_ERROR(10010, "没有指定类型");
    private long code;

    private java.lang.String msg;

    PostErrorCode(long code, java.lang.String msg) {
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