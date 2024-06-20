package cn.meteor.beyondclouds.modules.content.exception;
/**
 * 项目业务异常类
 */
public class ContentServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public ContentServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ContentServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}