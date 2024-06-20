package cn.meteor.beyondclouds.modules.feedback.exception;
/**
 *
 * @author 胡学良
 * @since 2020/2/14
 */
public class FeedbackServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public FeedbackServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public FeedbackServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}