package cn.meteor.beyondclouds.modules.content.enums;
/**
 *
 * @author 段启岩
 */
public enum ContentErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    CONTENT_NOT_FOUND(8001, "不存在该内容"),
    CONTENT_TYPE_ERROR(8002, "type错误, 可用的type = {0:幻灯, 1:普通文章, 2:广告}");
    private long code;

    private java.lang.String msg;

    ContentErrorCode(long code, java.lang.String msg) {
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