package cn.meteor.beyondclouds.modules.message.exception;
import lombok.Data;
/**
 * 话题业务异常
 *
 * @author 胡明森
 * @since 2020/1/28
 */
@lombok.Data
public class MessageServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public MessageServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public MessageServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        this(errorCode.code(), errorCode.msg());
    }
}