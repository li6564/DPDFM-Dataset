package cn.meteor.beyondclouds.common.exception;
/**
 *
 * @author meteor
 */
public class RedisOperationException extends java.lang.RuntimeException {
    public RedisOperationException() {
    }

    public RedisOperationException(java.lang.String message) {
        super(message);
    }

    public RedisOperationException(java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
    }
}