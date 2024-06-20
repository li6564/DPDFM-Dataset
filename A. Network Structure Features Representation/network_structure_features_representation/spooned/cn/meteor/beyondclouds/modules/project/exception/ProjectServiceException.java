package cn.meteor.beyondclouds.modules.project.exception;
/**
 * 项目业务异常类
 */
public class ProjectServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public ProjectServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ProjectServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}