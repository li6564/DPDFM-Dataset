package cn.meteor.beyondclouds.modules.feedback.form;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 投诉举报
 *
 * @author meteor
 */
@lombok.Data
public class ComplaintForm {
    /**
     * 举报原因：
     *  违法违禁
     *  政治敏感
     *  色情低俗
     *  垃圾广告
     *  内容侵权
     *  其它
     */
    @javax.validation.constraints.NotEmpty(message = "举报原因不能为空")
    private java.lang.String feedbackReason;

    /**
     * 举报描述
     */
    private java.lang.String content;

    /**
     * 要举报的链接（必填）
     */
    @javax.validation.constraints.NotEmpty(message = "举报链接不能为空")
    private java.lang.String link;

    /**
     * 举报人手机号(可选)
     */
    private java.lang.String mobile;

    /**
     * 举报的图片（可选）
     */
    private java.lang.String picture;
}