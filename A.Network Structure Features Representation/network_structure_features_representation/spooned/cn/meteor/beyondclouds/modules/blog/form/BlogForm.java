package cn.meteor.beyondclouds.modules.blog.form;
import lombok.Data;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:36
 */
@lombok.Data
public class BlogForm {
    @javax.validation.constraints.NotNull(message = "标题不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @javax.validation.constraints.NotEmpty(message = "标题不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的标题", groups = cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    private java.lang.String blogTitle;

    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的原文链接")
    private java.lang.String originLink;

    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的摘要")
    private java.lang.String blogAbstract;

    @javax.validation.constraints.NotNull(message = "博客类型不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    private java.lang.Integer categoryId;

    @javax.validation.constraints.NotNull(message = "内容不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @javax.validation.constraints.NotEmpty(message = "内容不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的博客内容", groups = cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    private java.lang.String content;

    @javax.validation.constraints.NotNull(message = "html内容不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @javax.validation.constraints.NotEmpty(message = "html内容不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的博客html内容", groups = cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    private java.lang.String contentHtml;

    @cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank(message = "topicId内容不能为空")
    private java.util.List<java.lang.String> topicIds;

    @javax.validation.constraints.Size(max = 5, message = "标签个数不能超过5个")
    @cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank(message = "tagId内容不能为空")
    private java.util.List<java.lang.String> tagIds;

    @javax.validation.constraints.NotNull(message = "浏览权限不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    private java.lang.Integer viewPrivileges;

    @javax.validation.constraints.NotNull(message = "评论权限不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    private java.lang.Boolean allowComment;

    @javax.validation.constraints.NotNull(message = "转载权限不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    private java.lang.Boolean allowForward;

    // @NotNull(message = "封面图不能为空", groups = InsertGroup.class)
    // @NotEmpty(message = "封面图不能为空", groups = InsertGroup.class)
    // @NullOrNotBlank(message = "请传入有效的封面图链接", groups = UpdateGroup.class)
    private java.lang.String cover;
}