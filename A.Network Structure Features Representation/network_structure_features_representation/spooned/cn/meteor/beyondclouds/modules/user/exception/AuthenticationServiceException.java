package cn.meteor.beyondclouds.modules.user.exception;
/**
 *
 * @author meteor
 */
public class AuthenticationServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public AuthenticationServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public AuthenticationServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}