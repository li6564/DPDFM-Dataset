package cn.meteor.beyondclouds.modules.tag.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 胡明森
 * @since 2020/1/28
 */
@io.swagger.annotations.ApiModel("创建标签表单")
@lombok.Data
public class CreateTagForm {
    @io.swagger.annotations.ApiModelProperty("标签名称")
    @javax.validation.constraints.NotEmpty(message = "标签名称不能为空")
    private java.lang.String tagName;

    @io.swagger.annotations.ApiModelProperty("标签类型")
    @javax.validation.constraints.NotNull
    private java.lang.Integer tagType;
}