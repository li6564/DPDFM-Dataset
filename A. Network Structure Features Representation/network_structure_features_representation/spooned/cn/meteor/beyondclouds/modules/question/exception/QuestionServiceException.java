package cn.meteor.beyondclouds.modules.question.exception;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
public class QuestionServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public QuestionServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public QuestionServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}