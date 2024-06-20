package cn.meteor.beyondclouds.modules.blog.exception;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:17
 */
public class BlogCommentServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public BlogCommentServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BlogCommentServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode.code(), errorCode.msg());
    }
}