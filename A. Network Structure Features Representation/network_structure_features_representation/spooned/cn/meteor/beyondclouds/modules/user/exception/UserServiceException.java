package cn.meteor.beyondclouds.modules.user.exception;
/**
 * 用户业务异常
 *
 * @author meteor
 */
public class UserServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public UserServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public UserServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        this(errorCode.code(), errorCode.msg());
    }
}