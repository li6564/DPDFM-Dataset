package cn.meteor.beyondclouds.modules.feedback.form;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 意见反馈
 *
 * @author meteor
 */
@lombok.Data
public class FeedbackForm {
    /**
     * 反馈：
     *  网站建议
     *  功能提议
     *  其它
     */
    @javax.validation.constraints.NotEmpty(message = "反馈类型不能为空")
    private java.lang.String feedbackReason;

    /**
     * 反馈描述
     */
    @javax.validation.constraints.NotEmpty(message = "反馈信息不能为空")
    private java.lang.String content;

    /**
     * 反馈的链接（可选）
     */
    private java.lang.String link;

    /**
     * 反馈的图片（可选）
     */
    private java.lang.String picture;

    /**
     * 反馈人手机号（必填，还得通过验证码验证才行）
     */
    private java.lang.String mobile;
}