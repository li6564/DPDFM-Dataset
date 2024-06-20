package cn.meteor.beyondclouds.core.api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
/**
 * Restful Api 响应，可以用来包装请求结果
 *
 * @author meteor
 */
@lombok.ToString
@lombok.Data
@io.swagger.annotations.ApiModel("RestfulApi返回结果包装器")
public class Response<T> {
    private static final long STATUS_SUCCESS_CODE = 0;

    private static final java.lang.String STATUS_SUCCESS_MSG = "操作成功";

    /**
     * 返回状态
     */
    @io.swagger.annotations.ApiModelProperty("操作状态")
    private long code;

    /**
     * 返回信息
     */
    @io.swagger.annotations.ApiModelProperty("返回信息")
    private java.lang.String msg;

    /**
     * 返回数据
     */
    @io.swagger.annotations.ApiModelProperty("数据")
    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 全参构造函数
     *
     * @param code
     * @param msg
     * @param data
     */
    public Response(long code, java.lang.String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    /**
     * 只有响应码和响应信息的构造函数
     *
     * @param code
     * @param msg
     */
    public Response(long code, java.lang.String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 带数据的成功返回
     *
     * @param data
     * @param <T>
     * @return  */
    public static <T> cn.meteor.beyondclouds.core.api.Response<T> success(T data) {
        return new cn.meteor.beyondclouds.core.api.Response<>(cn.meteor.beyondclouds.core.api.Response.STATUS_SUCCESS_CODE, cn.meteor.beyondclouds.core.api.Response.STATUS_SUCCESS_MSG, data);
    }

    /**
     * 不带数据的成功返回
     *
     * @param <T>
     * @return  */
    public static <T> cn.meteor.beyondclouds.core.api.Response<T> success() {
        return new cn.meteor.beyondclouds.core.api.Response<>(cn.meteor.beyondclouds.core.api.Response.STATUS_SUCCESS_CODE, cn.meteor.beyondclouds.core.api.Response.STATUS_SUCCESS_MSG);
    }

    /**
     * 自定义状态的失败返回
     *
     * @param errorCode
     * @param <T>
     * @return  */
    public static <T> cn.meteor.beyondclouds.core.api.Response<T> error(cn.meteor.beyondclouds.core.IErrorCode errorCode) {
        return new cn.meteor.beyondclouds.core.api.Response<>(errorCode.code(), errorCode.msg());
    }

    /**
     * 自定义状态的失败返回
     *
     * @return  */
    public static cn.meteor.beyondclouds.core.api.Response error(long code, java.lang.String msg) {
        return new cn.meteor.beyondclouds.core.api.Response(code, msg);
    }

    /**
     * 将业务层异常转化为失败信息返回
     *
     * @return  */
    public static cn.meteor.beyondclouds.core.api.Response error(cn.meteor.beyondclouds.core.exception.ServiceException serviceException) {
        return new cn.meteor.beyondclouds.core.api.Response(serviceException.getErrorCode(), serviceException.getErrorMsg());
    }

    /**
     * 默认状态的失败返回
     *
     * @param <T>
     * @return  */
    public static <T> cn.meteor.beyondclouds.core.api.Response<T> error() {
        return new cn.meteor.beyondclouds.core.api.Response<>(cn.meteor.beyondclouds.common.enums.ErrorCode.OPERATION_FAILED.code(), cn.meteor.beyondclouds.common.enums.ErrorCode.OPERATION_FAILED.msg());
    }

    /**
     * 传参错误
     *
     * @return  */
    public static cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.vo.FieldError> fieldError(java.lang.String field, java.lang.String errorMsg) {
        return new cn.meteor.beyondclouds.core.api.Response<>(cn.meteor.beyondclouds.common.enums.ErrorCode.FIELD_CHECK_FAILURE.code(), cn.meteor.beyondclouds.common.enums.ErrorCode.FIELD_CHECK_FAILURE.msg(), cn.meteor.beyondclouds.common.vo.FieldError.of(field, errorMsg));
    }

    /**
     * 传参错误
     *
     * @return  */
    public static cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.vo.FieldError> fieldError(org.springframework.validation.FieldError fieldError) {
        return cn.meteor.beyondclouds.core.api.Response.fieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }
}