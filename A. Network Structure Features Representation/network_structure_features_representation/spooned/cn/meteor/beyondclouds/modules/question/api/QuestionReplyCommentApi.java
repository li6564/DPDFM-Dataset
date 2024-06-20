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
 * @since 2020/2/2
 */
@io.swagger.annotations.Api(tags = "问题回复的评论Api")
@cn.meteor.beyondclouds.modules.question.api.RestController
@cn.meteor.beyondclouds.modules.question.api.RequestMapping("/api")
public class QuestionReplyCommentApi {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionReplyCommentApi(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService) {
        this.questionReplyCommentService = questionReplyCommentService;
    }

    /**
     * 发表评论
     *
     * @param questionReplyCommentForm
     * 		评论表单
     * @param bindingResult
     * 		校验信息
     * @param replyId
     * 		回复ID
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("发表评论")
    @cn.meteor.beyondclouds.modules.question.api.PostMapping("/question/reply/{replyId}/comment")
    public cn.meteor.beyondclouds.core.api.Response publishReplyComment(@cn.meteor.beyondclouds.modules.question.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.question.form.QuestionReplyCommentForm questionReplyCommentForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        try {
            questionReplyCommentService.publishReplyComment(((java.lang.String) (subject.getId())), replyId, questionReplyCommentForm.getParentId(), questionReplyCommentForm.getComment());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 删除评论
     *
     * @param commentId
     * 		评论ID
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("删除评论")
    @cn.meteor.beyondclouds.modules.question.api.DeleteMapping("/question/reply/comment/{commentId}")
    public cn.meteor.beyondclouds.core.api.Response deleteReplyComment(@cn.meteor.beyondclouds.modules.question.api.PathVariable("commentId")
    java.lang.String commentId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            questionReplyCommentService.deleteReplyComment(((java.lang.String) (subject.getId())), commentId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 评论列表
     *
     * @param replyId
     * 		回复ID
     * @param pageForm
     * 		分页表单
     * @param parentId
     * 		父评论ID
     * @return 评论列表
     */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("评论列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/reply/{replyId}/comments")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment>> getReplyComments(@cn.meteor.beyondclouds.modules.question.api.PathVariable("replyId")
    java.lang.String replyId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.RequestParam(value = "parentId", required = false)
    java.lang.String parentId) {
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> replyCommentPage = questionReplyCommentService.getReplyCommentPage(pageForm.getPage(), pageForm.getSize(), replyId, parentId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> questionReplyCommentPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(replyCommentPage);
            return cn.meteor.beyondclouds.core.api.Response.success(questionReplyCommentPageDTO);
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}