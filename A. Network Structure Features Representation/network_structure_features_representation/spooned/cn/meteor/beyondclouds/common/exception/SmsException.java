package cn.meteor.beyondclouds.common.exception;
/**
 * 短信相关异常
 *
 * @author meteor
 */
public class SmsException extends java.lang.Exception {
    public SmsException() {
    }

    public SmsException(java.lang.String message) {
        super(message);
    }

    public SmsException(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }
}