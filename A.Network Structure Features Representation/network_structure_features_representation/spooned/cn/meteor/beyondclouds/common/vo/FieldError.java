package cn.meteor.beyondclouds.common.vo;
import lombok.Data;
/**
 * 字段错误，前端传过来的值可能通过不了校验规则，这时候可以把出错的字段和错误信息封装成FieldError对象
 *
 * @author meteor
 */
@lombok.Data
public class FieldError {
    /**
     * 出错的字段名称
     */
    private java.lang.String field;

    /**
     * 错误信息
     */
    private java.lang.String errMsg;

    public FieldError(java.lang.String field, java.lang.String errMsg) {
        this.field = field;
        this.errMsg = errMsg;
    }

    public static cn.meteor.beyondclouds.common.vo.FieldError of(java.lang.String field, java.lang.String errMsg) {
        return new cn.meteor.beyondclouds.common.vo.FieldError(field, errMsg);
    }
}