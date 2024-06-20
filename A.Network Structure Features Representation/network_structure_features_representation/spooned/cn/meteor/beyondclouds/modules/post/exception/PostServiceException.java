package cn.meteor.beyondclouds.modules.post.exception;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 9:43
 */
public class PostServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public PostServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public PostServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}