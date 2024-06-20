package cn.meteor.beyondclouds.modules.question.exception;
/**
 *
 * @author 胡学良
 * @since 2020/2/1
 */
public class QuestionReplyServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public QuestionReplyServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public QuestionReplyServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}