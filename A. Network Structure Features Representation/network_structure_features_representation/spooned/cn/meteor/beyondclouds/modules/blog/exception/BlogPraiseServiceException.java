package cn.meteor.beyondclouds.modules.blog.exception;
/**
 *
 * @author gaoTong
 * @date 2020/2/20 10:55
 */
public class BlogPraiseServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public BlogPraiseServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BlogPraiseServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode.code(), errorCode.msg());
    }
}