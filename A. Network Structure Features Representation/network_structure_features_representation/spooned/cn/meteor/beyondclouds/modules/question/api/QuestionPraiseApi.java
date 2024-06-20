package cn.meteor.beyondclouds.modules.question.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 9:27
 */
@io.swagger.annotations.Api(tags = "问题点赞API")
@cn.meteor.beyondclouds.modules.question.api.RestController
@cn.meteor.beyondclouds.modules.question.api.RequestMapping("/api")
public class QuestionPraiseApi {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionPraiseService(cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService) {
        this.questionPraiseService = questionPraiseService;
    }

    @io.swagger.annotations.ApiOperation("问题点赞")
    @cn.meteor.beyondclouds.modules.question.api.PostMapping("/question/{questionId}/praise")
    public cn.meteor.beyondclouds.core.api.Response questionPraise(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            questionPraiseService.questionPraise(currentUserId, questionId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("问题取消点赞")
    @cn.meteor.beyondclouds.modules.question.api.DeleteMapping("/question/{questionId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deleteQuestionPraise(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            questionPraiseService.deleteQuestionPraise(currentUserId, questionId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("问题回复点赞")
    @cn.meteor.beyondclouds.modules.question.api.PostMapping("/question/reply/{replyId}/praise")
    public cn.meteor.beyondclouds.core.api.Response praise(@cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            questionPraiseService.questionReplyPraise(currentUserId, replyId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("问题回复取消点赞")
    @cn.meteor.beyondclouds.modules.question.api.DeleteMapping("/question/reply/{replyId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deletePraise(@cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            questionPraiseService.deleteQuestionReplyPraise(currentUserId, replyId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("问题点赞列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/{questionId}/praises")
    public cn.meteor.beyondclouds.core.api.Response getPraises(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = questionPraiseService.getQuestionPraises(pageForm.getPage(), pageForm.getSize(), questionId);
        return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
    }
}