package cn.meteor.beyondclouds.modules.blog.form;
import lombok.Data;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 16:15
 */
@lombok.Data
public class BlogCommentForm {
    private java.lang.Integer parentId;

    @javax.validation.constraints.NotEmpty(message = "评论内容不能为空")
    private java.lang.String comment;
}