package cn.meteor.beyondclouds.modules.sms.exception;
/**
 * 短信业务异常
 *
 * @author meteor
 */
public class SmsServiceException extends cn.meteor.beyondclouds.core.exception.ServiceException {
    public SmsServiceException(long errorCode, java.lang.String errorMsg) {
        super(errorCode, errorMsg);
    }

    public SmsServiceException(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        super(errorCode);
    }
}