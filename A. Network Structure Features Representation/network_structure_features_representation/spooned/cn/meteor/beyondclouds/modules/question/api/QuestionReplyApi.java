package cn.meteor.beyondclouds.modules.question.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author 胡学良
 * @since 2020/2/1
 */
@io.swagger.annotations.Api(tags = "问题回复Api")
@cn.meteor.beyondclouds.modules.question.api.RestController
@cn.meteor.beyondclouds.modules.question.api.RequestMapping("/api")
public class QuestionReplyApi {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionReplyApi(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService) {
        this.questionReplyService = questionReplyService;
    }

    /**
     * 回复问题
     *
     * @param questionId
     * 		问题ID
     * @param replyForm
     * 		回复内容
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("发布回复")
    @cn.meteor.beyondclouds.modules.question.api.PostMapping("/question/{questionId}/reply")
    public cn.meteor.beyondclouds.core.api.Response replyQuestion(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId, @javax.validation.Valid
    @cn.meteor.beyondclouds.modules.question.api.RequestBody
    cn.meteor.beyondclouds.modules.question.form.QuestionReplyForm replyForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        try {
            questionReplyService.replyQuestion(questionId, replyForm.getReply(), ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 采纳回复
     *
     * @param questionId
     * 		问题ID
     * @param replyId
     * 		回复ID
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("采纳回复")
    @cn.meteor.beyondclouds.modules.question.api.PutMapping("/question/reply/{replyId}/adoption")
    public cn.meteor.beyondclouds.core.api.Response adoptReply(java.lang.String questionId, @cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            questionReplyService.adoptReply(questionId, replyId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 问题的回复列表
     *
     * @param pageForm
     * 		分页表单
     * @param questionId
     * 		问题ID
     * @return 问题的回复列表
     */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("问题的回复列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/{questionId}/replies")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.QuestionReply>> getReplies(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId) {
        // 获取问题的回复列表
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyPage = questionReplyService.getReplies(pageForm.getPage(), pageForm.getSize(), questionId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(questionReplyPage);
            return cn.meteor.beyondclouds.core.api.Response.success(questionReplyPageDTO);
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 删除回复
     *
     * @param replyId
     * 		回复ID
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("删除回复")
    @cn.meteor.beyondclouds.modules.question.api.DeleteMapping("/question/reply/{replyId}")
    public cn.meteor.beyondclouds.core.api.Response deleteReply(@cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            questionReplyService.deleteReply(replyId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消采纳回复")
    @cn.meteor.beyondclouds.modules.question.api.PutMapping("/question/reply/{replyId}/cancelAdoption")
    public cn.meteor.beyondclouds.core.api.Response cancelQuestionAdoption(java.lang.String questionId, @cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            questionReplyService.cancelReplyAdoption(questionId, replyId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}