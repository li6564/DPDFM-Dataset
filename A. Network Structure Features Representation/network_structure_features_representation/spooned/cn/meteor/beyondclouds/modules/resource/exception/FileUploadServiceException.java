package cn.meteor.beyondclouds.modules.resource.exception;
/**
 * 文件上传业务异常
 *
 * @author meteor
 */
public class FileUploadServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public FileUploadServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public FileUploadServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}