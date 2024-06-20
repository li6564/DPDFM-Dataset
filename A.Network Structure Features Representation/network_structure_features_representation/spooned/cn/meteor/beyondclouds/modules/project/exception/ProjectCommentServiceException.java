package cn.meteor.beyondclouds.modules.project.exception;
/**
 * 项目业务异常类
 */
public class ProjectCommentServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public ProjectCommentServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ProjectCommentServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}