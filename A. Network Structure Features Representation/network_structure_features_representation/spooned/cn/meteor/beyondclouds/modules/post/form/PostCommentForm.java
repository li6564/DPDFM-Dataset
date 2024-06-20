package cn.meteor.beyondclouds.modules.post.form;
import lombok.Data;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 21:28
 */
@lombok.Data
public class PostCommentForm {
    private java.lang.Integer parentId;

    @javax.validation.constraints.NotEmpty(message = "评论内容不能为空")
    private java.lang.String comment;
}