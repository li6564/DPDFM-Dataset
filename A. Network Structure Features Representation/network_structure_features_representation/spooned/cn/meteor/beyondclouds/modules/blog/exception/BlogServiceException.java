package cn.meteor.beyondclouds.modules.blog.exception;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:16
 */
public class BlogServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public BlogServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BlogServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}