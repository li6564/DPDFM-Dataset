package cn.meteor.beyondclouds.modules.tag.exception;
import lombok.Data;
/**
 * 标签业务异常
 *
 * @author 胡明森
 * @since 2020/2/2
 */
@lombok.Data
public class TagServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public TagServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public TagServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        this(errorCode.code(), errorCode.msg());
    }
}