package cn.meteor.beyondclouds.modules.question.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
@io.swagger.annotations.ApiModel(value = "QuestionReplyForm", description = "问题回复表单")
@lombok.Data
public class QuestionReplyForm {
    private static final long serialVersionUID = 1L;

    @javax.validation.constraints.NotEmpty(message = "回复不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    private java.lang.String reply;
}