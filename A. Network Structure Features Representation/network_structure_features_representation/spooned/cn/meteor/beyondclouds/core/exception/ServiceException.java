package cn.meteor.beyondclouds.core.exception;
import lombok.Data;
/**
 * 业务异常，在执行业务代码出错时可以抛出此类异常，每个业务模块都需要继承该类，形成针对特定业务的异常
 *
 * @author meteor
 */
@lombok.Data
public class ServiceException extends java.lang.Exception {
    /**
     * 错误码
     */
    private long errorCode;

    /**
     * 错误消息
     */
    private java.lang.String errorMsg;

    /**
     * 构造函数-通过errorCode和errorMsg构造异常对象
     *
     * @param errorCode
     * 		错误码
     * @param errorMsg
     * 		错误信息
     */
    public ServiceException(long errorCode, java.lang.String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 构造函数-通过IErrorCode对象构造异常对象
     *
     * @param errorCode
     */
    public ServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        this(errorCode.code(), errorCode.msg());
    }
}