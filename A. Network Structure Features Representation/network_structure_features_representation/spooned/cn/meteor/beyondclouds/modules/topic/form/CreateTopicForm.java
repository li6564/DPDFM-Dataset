package cn.meteor.beyondclouds.modules.topic.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 胡明森
 * @since 2020/1/28
 */
@io.swagger.annotations.ApiModel("创建话题表单")
@lombok.Data
public class CreateTopicForm {
    @io.swagger.annotations.ApiModelProperty("话题名称")
    @javax.validation.constraints.NotEmpty(message = "话题名称不能为空")
    private java.lang.String topicName;
}