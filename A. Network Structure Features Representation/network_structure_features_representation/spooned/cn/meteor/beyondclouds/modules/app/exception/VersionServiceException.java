package cn.meteor.beyondclouds.modules.app.exception;
/**
 *
 * @author meteor
 */
public class VersionServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public VersionServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public VersionServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}