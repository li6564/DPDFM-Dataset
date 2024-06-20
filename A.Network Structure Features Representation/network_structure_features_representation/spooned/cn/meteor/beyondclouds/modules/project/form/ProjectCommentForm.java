package cn.meteor.beyondclouds.modules.project.form;
import lombok.Data;
/**
 * 项目评论表单
 *
 * @author 段启岩
 */
@lombok.Data
public class ProjectCommentForm {
    private java.lang.Integer parentId;

    @javax.validation.constraints.NotEmpty(message = "评论内容不能为空")
    private java.lang.String comment;
}