package cn.meteor.beyondclouds.modules.question.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
@io.swagger.annotations.ApiModel("问题详情")
@lombok.Data
public class QuestionDetailDTO extends cn.meteor.beyondclouds.modules.question.entity.Question {
    /**
     * 当前用户是否关注作者
     */
    private java.lang.Boolean followedAuthor;

    private java.lang.Boolean praised;

    @io.swagger.annotations.ApiModelProperty("详情")
    private java.lang.String content;

    @io.swagger.annotations.ApiModelProperty("详情html")
    private java.lang.String contentHtml;
}