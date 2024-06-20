package cn.meteor.beyondclouds.core.exception;
/**
 * 授权异常
 *
 * @author meteor
 */
public class AuthorizationException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public AuthorizationException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public AuthorizationException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}