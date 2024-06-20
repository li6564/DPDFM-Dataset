package cn.meteor.beyondclouds.modules.post.exception;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 9:44
 */
public class PostCommentServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public PostCommentServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public PostCommentServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}