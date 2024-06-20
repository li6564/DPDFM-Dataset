package cn.meteor.beyondclouds.modules.project.api;
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
@io.swagger.annotations.Api(tags = "项目点赞API")
@cn.meteor.beyondclouds.modules.project.api.RestController
@cn.meteor.beyondclouds.modules.project.api.RequestMapping("/api")
public class ProjectPraiseApi {
    private cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectPraiseService(cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService) {
        this.projectPraiseService = projectPraiseService;
    }

    @io.swagger.annotations.ApiOperation("项目点赞")
    @cn.meteor.beyondclouds.modules.project.api.PostMapping("/project/{projectId}/praise")
    public cn.meteor.beyondclouds.core.api.Response praiseProject(@cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.String projectId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            projectPraiseService.praiseProject(currentUserId, projectId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消项目点赞")
    @cn.meteor.beyondclouds.modules.project.api.DeleteMapping("/project/{projectId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deleteProjectPraise(@cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.String projectId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            projectPraiseService.deleteProjectPraise(currentUserId, projectId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("项目评论点赞")
    @cn.meteor.beyondclouds.modules.project.api.PostMapping("/project/comment/{commentId}/praise")
    public cn.meteor.beyondclouds.core.api.Response praiseProjectComment(@cn.meteor.beyondclouds.modules.project.api.PathVariable("commentId")
    java.lang.String commentId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            projectPraiseService.praiseProjectComment(currentUserId, commentId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消项目评论点赞")
    @cn.meteor.beyondclouds.modules.project.api.DeleteMapping("/project/comment/{commentId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deleteProjectCommentPraise(@cn.meteor.beyondclouds.modules.project.api.PathVariable("commentId")
    java.lang.String commentId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            projectPraiseService.deleteProjectCommentPraise(currentUserId, commentId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("项目点赞列表")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/project/{projectId}/praises")
    public cn.meteor.beyondclouds.core.api.Response getPraises(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.String projectId) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = projectPraiseService.getProjectPraises(pageForm.getPage(), pageForm.getSize(), projectId);
        return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
    }
}