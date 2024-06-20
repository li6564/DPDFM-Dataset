package cn.meteor.beyondclouds.common.exception;
/**
 * Oss异常
 *
 * @author meteor
 */
public class OssException extends java.lang.Exception {
    public OssException() {
    }

    public OssException(java.lang.String message) {
        super(message);
    }

    public OssException(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }
}