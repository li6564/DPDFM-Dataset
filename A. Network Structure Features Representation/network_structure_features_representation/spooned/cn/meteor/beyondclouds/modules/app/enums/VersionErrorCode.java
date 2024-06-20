package cn.meteor.beyondclouds.modules.app.enums;
/**
 *
 * @author meteor
 */
public enum VersionErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    VERSION_FETCH_FAILURE(20000, "版本信息获取失败！");
    private java.lang.Integer code;

    private java.lang.String msg;

    VersionErrorCode(java.lang.Integer code, java.lang.String msg) {
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