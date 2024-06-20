package cn.meteor.beyondclouds.common.form;
import lombok.Data;
/**
 * 分页表单
 *
 * @author 段启岩
 */
@lombok.Data
public class PageForm {
    @javax.validation.constraints.NotNull(message = "请传入参数page")
    private java.lang.Integer page;

    @javax.validation.constraints.NotNull(message = "请传入参数size")
    private java.lang.Integer size;
}