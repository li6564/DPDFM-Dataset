package cn.meteor.beyondclouds.modules.question.form;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
@io.swagger.annotations.ApiModel(value = "QuestionForm", description = "问题表单")
@lombok.Data
public class QuestionForm {
    private static final long serialVersionUID = 1L;

    @io.swagger.annotations.ApiModelProperty("类别ID")
    @javax.validation.constraints.NotNull(message = "问题类别ID不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    private java.lang.Integer categoryId;

    @io.swagger.annotations.ApiModelProperty("标题")
    @javax.validation.constraints.NotNull(message = "问题标题不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @javax.validation.constraints.NotEmpty(message = "问题标题不能为空", groups = cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    @javax.validation.constraints.Size(max = 256, message = "问题标题太长")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的标题", groups = cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    private java.lang.String questionTitle;

    @io.swagger.annotations.ApiModelProperty("摘要")
    @javax.validation.constraints.Size(max = 256, message = "问题摘要太长")
    @cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank(message = "请传入有效的摘要", groups = cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    private java.lang.String questionAbstract;

    @io.swagger.annotations.ApiModelProperty("标签")
    @javax.validation.constraints.Size(max = 5, message = "标签个数不能超过5个")
    @cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank(message = "tag内容不能为空")
    private java.util.List<java.lang.String> tagIds;

    @io.swagger.annotations.ApiModelProperty("话题ID")
    @cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank(message = "topic内容不能为空")
    private java.util.List<java.lang.String> topicIds;

    @io.swagger.annotations.ApiModelProperty("详情")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("详情html")
    private java.lang.String contentHtml;
}