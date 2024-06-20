package cn.meteor.beyondclouds.modules.question.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author 胡学良
 * @since 2020/1/31
 */
@io.swagger.annotations.Api(tags = "问题Api")
@cn.meteor.beyondclouds.modules.question.api.RestController
@cn.meteor.beyondclouds.modules.question.api.RequestMapping("/api")
public class QuestionApi {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionTagService questionTagService;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionApi(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService, cn.meteor.beyondclouds.modules.question.service.IQuestionTagService questionTagService) {
        this.questionService = questionService;
        this.questionTagService = questionTagService;
    }

    /**
     * 发布问题
     *
     * @param questionForm
     * 		问题表单
     * @param result
     * 		校验信息
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("发布问题")
    @cn.meteor.beyondclouds.modules.question.api.PostMapping("/question")
    public cn.meteor.beyondclouds.core.api.Response postQuestion(@cn.meteor.beyondclouds.modules.question.api.RequestBody
    @org.springframework.validation.annotation.Validated(cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    cn.meteor.beyondclouds.modules.question.form.QuestionForm questionForm, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        // 将问题表单转换为问题对象
        cn.meteor.beyondclouds.modules.question.entity.Question question = new cn.meteor.beyondclouds.modules.question.entity.Question();
        org.springframework.beans.BeanUtils.copyProperties(questionForm, question);
        question.setCategoryId(questionForm.getCategoryId());
        // 设置用户ID（问题发布者ID）
        question.setUserId(((java.lang.String) (subject.getId())));
        // 发布问题
        try {
            questionService.postQuestion(question, questionForm.getContent(), questionForm.getContentHtml(), questionForm.getTagIds(), questionForm.getTopicIds());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 删除问题
     *
     * @param questionId
     * 		问题ID
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("删除问题")
    @cn.meteor.beyondclouds.modules.question.api.DeleteMapping("/question/{questionId}")
    public cn.meteor.beyondclouds.core.api.Response deleteQuestion(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            questionService.deleteQuestion(questionId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 修改问题
     *
     * @param questionId
     * 		问题ID
     * @param questionForm
     * 		问题表单
     * @param result
     * 		校验信息
     * @param subject
     * 		访问者信息
     * @return default
     */
    @io.swagger.annotations.ApiOperation("修改问题")
    @cn.meteor.beyondclouds.modules.question.api.PutMapping("/question/{questionId}")
    public cn.meteor.beyondclouds.core.api.Response modifyQuestion(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId, @cn.meteor.beyondclouds.modules.question.api.RequestBody
    @org.springframework.validation.annotation.Validated(cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    cn.meteor.beyondclouds.modules.question.form.QuestionForm questionForm, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        // 将问题表单转换为问题对象
        cn.meteor.beyondclouds.modules.question.entity.Question question = new cn.meteor.beyondclouds.modules.question.entity.Question();
        org.springframework.beans.BeanUtils.copyProperties(questionForm, question);
        // 2.设置问题ID
        question.setQuestionId(questionId);
        // 3.设置用户ID
        question.setUserId(((java.lang.String) (subject.getId())));
        // 4.修改问题
        try {
            questionService.updateQuestion(question, questionForm.getContent(), questionForm.getContentHtml(), questionForm.getTagIds(), questionForm.getTopicIds());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 问题详情
     *
     * @param questionId
     * 		问题ID
     * @return 问题详情
     */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("问题详情")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/{questionId}")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO> questionDetails(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(paramName = "questionId", type = cn.meteor.beyondclouds.core.flow.ParamType.QUESTION)
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        boolean updateViewNum = false;
        if (cn.meteor.beyondclouds.util.AccessInfoUtils.hasFieldInfo(accessInfo)) {
            if (accessInfo.getFieldVisitCount() == 0) {
                updateViewNum = true;
            }
        }
        cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO questionDetail;
        try {
            questionDetail = questionService.getQuestionDetail(questionId, subject, updateViewNum);
            return cn.meteor.beyondclouds.core.api.Response.success(questionDetail);
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException | cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 得到问题引用标签
     *
     * @param questionId
     * 		问题ID
     * @return 问题引用标签
     */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("得到问题的引用标签")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/{questionId}/tags")
    public cn.meteor.beyondclouds.core.api.Response<java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag>> questionTags(@cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId) {
        try {
            java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> tags = questionTagService.getQuestionTags(questionId);
            return cn.meteor.beyondclouds.core.api.Response.success(tags);
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 问答列表
     *
     * @param pageForm
     * 		分页表单
     * @return 问答列表
     */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("问答列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/questions")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getQuestions(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.RequestParam(value = "categoryId", required = false)
    java.lang.Integer categoryId) {
        // 获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = questionService.getQuestionPage(pageForm.getPage(), pageForm.getSize(), categoryId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(questionPage);
        return cn.meteor.beyondclouds.core.api.Response.success(questionPageDTO);
    }

    /**
     * 我创建的问答列表
     *
     * @param pageForm
     * 		分页表单
     * @param subject
     * 		访问者信息
     * @return 我创建的问答列表
     */
    @io.swagger.annotations.ApiOperation("我创建的问答列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/my/questions")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getMyQuestions(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 获取我的列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> myQuestionPage = questionService.getUserQuestionPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(myQuestionPage);
        return cn.meteor.beyondclouds.core.api.Response.success(questionPageDTO);
    }

    /**
     * 我参与的问答列表
     *
     * @param pageForm
     * 		分页表单
     * @param subject
     * 		访问者信息
     * @return 我参与的问答列表
     */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @io.swagger.annotations.ApiOperation("我参与的问答列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/my/question/participated")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getMyParticipateQuestions(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 获取我的列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = questionService.getUserParticipatePage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionReplyPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(questionPage);
        return cn.meteor.beyondclouds.core.api.Response.success(questionReplyPageDTO);
    }

    /**
     * 用户创建的问答列表
     *
     * @param pageForm
     * 		分页表单
     * @param userId
     * 		用户ID
     * @return 用户创建的问答列表
     */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("用户创建的问答列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/user/{userId}/questions")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getOthersQuestions(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.PathVariable("userId")
    java.lang.String userId) {
        // 获取别人的问答列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = questionService.getUserQuestionPage(pageForm.getPage(), pageForm.getSize(), userId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(questionPage);
        return cn.meteor.beyondclouds.core.api.Response.success(questionPageDTO);
    }

    /**
     * 用户参与的问答列表
     *
     * @param pageForm
     * 		分页表单
     * @param userId
     * 		用户ID
     * @return 用户参与的问答列表
     */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("用户参与的问答列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/user/{userId}/question/participated")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getUserParticipateQuestions(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.PathVariable("userId")
    java.lang.String userId) {
        // 获取用户参与的问答列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> userParticipateQuestion = questionService.getUserParticipatePage(pageForm.getPage(), pageForm.getSize(), userId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionReplyPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(userParticipateQuestion);
        return cn.meteor.beyondclouds.core.api.Response.success(questionReplyPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门回答")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/hots")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getHotsQuestions(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = questionService.getHotsQuestionsPage(pageForm.getPage(), pageForm.getSize());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(questionPage);
        return cn.meteor.beyondclouds.core.api.Response.success(questionPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("相关问答")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/question/{questionId}/recommends")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> questionRecommends(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.PathVariable("questionId")
    java.lang.String questionId) {
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = questionService.questionRecommends(pageForm.getPage(), pageForm.getSize(), questionId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> questionPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(questionPage);
            return cn.meteor.beyondclouds.core.api.Response.success(questionPageDTO);
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("得到标签下的问答列表")
    @cn.meteor.beyondclouds.modules.question.api.GetMapping("/tag/{tagId}/questions")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question>> getQuestionsByTagId(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.question.api.PathVariable("tagId")
    java.lang.String tagId) {
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> page = questionService.getQuestionsByTagId(pageForm.getPage(), pageForm.getSize(), tagId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.question.entity.Question> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(page);
            return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
        } catch (cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}