package cn.meteor.beyondclouds.modules.question.exception;
/**
 *
 * @author 胡学良
 * @since 2020/2/2
 */
public class QuestionReplyCommentServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public QuestionReplyCommentServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public QuestionReplyCommentServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}