package cn.meteor.beyondclouds.common.enums;
import lombok.AllArgsConstructor;
/**
 * 错误代码
 *
 * @author meteor
 */
@lombok.AllArgsConstructor
public enum ErrorCode implements cn.meteor.beyondclouds.core.IErrorCode {

    /**
     * 操作错误
     */
    OPERATION_FAILED(-1, "操作失败！"),
    /**
     * 参数校验错误
     * 前端传进来的参数没有通过校验规则
     */
    FIELD_CHECK_FAILURE(-2, "参数校验失败！"),
    DUPLICATE_SUBMISSION(-3, "访问太频繁");
    private java.lang.Integer code;

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