package cn.meteor.beyondclouds.modules.project.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * 项目评论接口
 *
 * @author 段启岩
 */
@io.swagger.annotations.Api(tags = "项目评论API")
@cn.meteor.beyondclouds.modules.project.api.RestController
@cn.meteor.beyondclouds.modules.project.api.RequestMapping("/api")
public class ProjectCommentApi {
    private cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService;

    @org.springframework.beans.factory.annotation.Autowired
    public ProjectCommentApi(cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService) {
        this.projectCommentService = projectCommentService;
    }

    /**
     * 发表评论
     *
     * @param projectId
     * @param projectCommentForm
     * @param bindingResult
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("发表评论")
    @cn.meteor.beyondclouds.modules.project.api.PostMapping("/project/{projectId}/comment")
    public cn.meteor.beyondclouds.core.api.Response publishProjectComment(@cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.Integer projectId, @cn.meteor.beyondclouds.modules.project.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.project.form.ProjectCommentForm projectCommentForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        try {
            // 发表评论
            projectCommentService.publishComment(projectId, projectCommentForm.getParentId(), projectCommentForm.getComment(), ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("删除评论")
    @cn.meteor.beyondclouds.modules.project.api.DeleteMapping("/project/comment/{commentId}")
    public cn.meteor.beyondclouds.core.api.Response deleterojectComment(@cn.meteor.beyondclouds.modules.project.api.PathVariable("commentId")
    java.lang.Integer commentId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            // 删除评论
            projectCommentService.deleteComment(commentId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("评论列表")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/project/{projectId}/comments")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.ProjectComment>> getProjectComments(@cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.Integer projectId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.project.api.RequestParam(value = "parentId", required = false)
    java.lang.Integer parentId) {
        try {
            // 根据用户获取列表并返回
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> commentPage = projectCommentService.getCommentPage(pageForm.getPage(), pageForm.getSize(), projectId, parentId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(commentPage);
            return cn.meteor.beyondclouds.core.api.Response.success(projectPageDTO);
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}