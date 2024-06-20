package cn.meteor.beyondclouds.common.handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
/**
 * 全局异常捕获器，捕获在Controller层未处理异常
 *
 * @author meteor
 */
@org.springframework.web.bind.annotation.RestControllerAdvice
public class GlobalExceptionHandler {
    private cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties beyondCloudsProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public GlobalExceptionHandler(cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties beyondCloudsProperties) {
        this.beyondCloudsProperties = beyondCloudsProperties;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(java.lang.Exception.class)
    public cn.meteor.beyondclouds.core.api.Response<java.lang.String> exceptionHandler(javax.servlet.http.HttpServletRequest request, java.lang.Exception e) {
        boolean debugMode = beyondCloudsProperties.getDebug();
        e.printStackTrace();
        if (e instanceof cn.meteor.beyondclouds.core.exception.ServiceException) {
            cn.meteor.beyondclouds.core.exception.ServiceException exception = ((cn.meteor.beyondclouds.core.exception.ServiceException) (e));
            return cn.meteor.beyondclouds.core.api.Response.error(exception.getErrorCode(), exception.getErrorMsg());
        } else if (e instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.common.enums.ErrorCode.OPERATION_FAILED.code(), e.getMessage());
        } else if (e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.resource.enums.FileUploadErrorCode.MAXIMUM_UPLOAD_SIZE_EXCEEDED);
        }
        {
            if (debugMode) {
                java.io.ByteArrayOutputStream byteBuff = new java.io.ByteArrayOutputStream();
                e.printStackTrace(new java.io.PrintStream(byteBuff));
                java.lang.String errTrace = byteBuff.toString();
                try {
                    byteBuff.close();
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                }
                return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.common.enums.ErrorCode.OPERATION_FAILED.code(), errTrace);
            } else {
                return cn.meteor.beyondclouds.core.api.Response.error();
            }
        }
    }
}