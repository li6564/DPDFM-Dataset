package cn.meteor.beyondclouds.modules.blog.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author gaoTong
 * @date 2020/1/31 16:13
 */
@io.swagger.annotations.Api(tags = "博客评论API")
@cn.meteor.beyondclouds.modules.blog.api.RestController
@cn.meteor.beyondclouds.modules.blog.api.RequestMapping("/api")
public class BlogCommentApi {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService;

    public BlogCommentApi(cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @io.swagger.annotations.ApiOperation("发表评论")
    @cn.meteor.beyondclouds.modules.blog.api.PostMapping("/blog/{blogId}/comment")
    public cn.meteor.beyondclouds.core.api.Response createComment(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId, @cn.meteor.beyondclouds.modules.blog.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.blog.form.BlogCommentForm blogCommentForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            blogCommentService.createComment(blogId, blogCommentForm.getParentId(), blogCommentForm.getComment(), ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException | cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("删除评论")
    @cn.meteor.beyondclouds.modules.blog.api.DeleteMapping("/blog/comment/{commentId}")
    public cn.meteor.beyondclouds.core.api.Response deleteBlogComment(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("commentId")
    java.lang.Integer commentId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            blogCommentService.deleteComment(commentId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("评论列表")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/blog/{blogId}/comments")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.BlogComment>> getBlogCommentPage(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String bLogId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.blog.api.RequestParam(value = "parent_id", required = false)
    java.lang.Integer parentId) {
        // 获取评论列表
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> commentPage = blogCommentService.getCommentPage(pageForm.getPage(), pageForm.getSize(), bLogId, parentId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(commentPage);
            return cn.meteor.beyondclouds.core.api.Response.success(blogCommentPageDTO);
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}