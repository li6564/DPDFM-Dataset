package cn.meteor.beyondclouds.modules.feedback.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-14
 */
@io.swagger.annotations.Api(tags = "反馈API")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api")
public class FeedbackApi {
    private cn.meteor.beyondclouds.modules.feedback.service.IFeedbackService feedbackService;

    @org.springframework.beans.factory.annotation.Autowired
    public FeedbackApi(cn.meteor.beyondclouds.modules.feedback.service.IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 意见反馈
     *
     * @param feedbackForm
     * @param bindingResult
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @org.springframework.web.bind.annotation.PostMapping("/feedback")
    @io.swagger.annotations.ApiOperation("意见反馈")
    public cn.meteor.beyondclouds.core.api.Response feedback(@org.springframework.web.bind.annotation.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.feedback.form.FeedbackForm feedbackForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        // TODO 实现反馈功能
        cn.meteor.beyondclouds.modules.feedback.entity.Feedback feedback = new cn.meteor.beyondclouds.modules.feedback.entity.Feedback();
        org.springframework.beans.BeanUtils.copyProperties(feedbackForm, feedback);
        try {
            feedbackService.feedback(feedback);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.feedback.exception.FeedbackServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 投诉举报
     *
     * @param complaintForm
     * @param bindingResult
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @org.springframework.web.bind.annotation.PostMapping("/complaint")
    @io.swagger.annotations.ApiOperation("投诉举报")
    public cn.meteor.beyondclouds.core.api.Response complaint(@org.springframework.web.bind.annotation.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.feedback.form.ComplaintForm complaintForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        // TODO 实现投诉举报功能
        cn.meteor.beyondclouds.modules.feedback.entity.Feedback complaint = new cn.meteor.beyondclouds.modules.feedback.entity.Feedback();
        org.springframework.beans.BeanUtils.copyProperties(complaintForm, complaint);
        feedbackService.complaint(complaint);
        return cn.meteor.beyondclouds.core.api.Response.success();
    }
}