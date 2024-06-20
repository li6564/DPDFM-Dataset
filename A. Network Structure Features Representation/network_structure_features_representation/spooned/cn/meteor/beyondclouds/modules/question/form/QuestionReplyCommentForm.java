package cn.meteor.beyondclouds.modules.question.form;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 回复评论表单
 *
 * @author 胡学良
 * @since 2020/2/2
 */
@lombok.Data
public class QuestionReplyCommentForm {
    @io.swagger.annotations.ApiModelProperty("父评论ID")
    private java.lang.String parentId;

    @io.swagger.annotations.ApiModelProperty("评论内容")
    @javax.validation.constraints.NotEmpty(message = "评论内容不能为空")
    private java.lang.String comment;
}