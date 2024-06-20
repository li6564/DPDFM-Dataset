package cn.meteor.beyondclouds.modules.blog.exception;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:18
 */
public class BlogCategoryServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public BlogCategoryServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BlogCategoryServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode.code(), errorCode.msg());
    }
}