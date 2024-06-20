package cn.meteor.beyondclouds.modules.blog.api;
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
@io.swagger.annotations.Api(tags = "博客点赞API")
@cn.meteor.beyondclouds.modules.blog.api.RestController
@cn.meteor.beyondclouds.modules.blog.api.RequestMapping("/api")
public class BlogPraiseApi {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogPraiseService(cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService) {
        this.blogPraiseService = blogPraiseService;
    }

    @io.swagger.annotations.ApiOperation("博客点赞")
    @cn.meteor.beyondclouds.modules.blog.api.PostMapping("/blog/{blogId}/praise")
    public cn.meteor.beyondclouds.core.api.Response bogPraise(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            blogPraiseService.praiseBlog(currentUserId, blogId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消博客点赞")
    @cn.meteor.beyondclouds.modules.blog.api.DeleteMapping("/blog/{blogId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deleteBlogPraise(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            blogPraiseService.deleteBlogPraise(currentUserId, blogId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("博客评论点赞")
    @cn.meteor.beyondclouds.modules.blog.api.PostMapping("/blog/comment{commentId}/praise")
    public cn.meteor.beyondclouds.core.api.Response blogCommentPraise(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("commentId")
    java.lang.String commentId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            blogPraiseService.praiseBlogComment(currentUserId, commentId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消博客评论点赞")
    @cn.meteor.beyondclouds.modules.blog.api.DeleteMapping("/blog/comment{commentId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deleteBlogCommentPraise(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("commentId")
    java.lang.String commentId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            blogPraiseService.deleteBlogCommentPraise(currentUserId, commentId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("博客点赞列表")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/blog/{blogId}/praises")
    public cn.meteor.beyondclouds.core.api.Response getPraises(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = blogPraiseService.getBlogPraises(pageForm.getPage(), pageForm.getSize(), blogId);
        return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
    }
}