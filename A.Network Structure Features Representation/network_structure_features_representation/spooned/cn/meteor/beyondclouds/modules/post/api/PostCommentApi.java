package cn.meteor.beyondclouds.modules.post.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 9:42
 */
@io.swagger.annotations.Api(tags = "动态评论API")
@cn.meteor.beyondclouds.modules.post.api.RestController
@cn.meteor.beyondclouds.modules.post.api.RequestMapping("/api")
public class PostCommentApi {
    private cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostCommentService(cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @io.swagger.annotations.ApiOperation("发布评论")
    @cn.meteor.beyondclouds.modules.post.api.PostMapping("/post/{postId}/comment")
    public cn.meteor.beyondclouds.core.api.Response publishComment(@cn.meteor.beyondclouds.modules.post.api.PathVariable("postId")
    java.lang.String postId, @cn.meteor.beyondclouds.modules.post.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.post.form.PostCommentForm postCommentForm, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        try {
            postCommentService.createComment(postId, postCommentForm.getParentId(), postCommentForm.getComment(), ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.core.exception.ServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("删除评论")
    @cn.meteor.beyondclouds.modules.post.api.DeleteMapping("/post/comment/{commentId}")
    public cn.meteor.beyondclouds.core.api.Response deleteComment(@cn.meteor.beyondclouds.modules.post.api.PathVariable("commentId")
    java.lang.Integer commentId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            postCommentService.deleteComment(commentId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("评论列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/post/{postId}/comments")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.entity.PostComment>> getPostCommentPage(@cn.meteor.beyondclouds.modules.post.api.PathVariable("postId")
    java.lang.String postId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.post.api.RequestParam(value = "parent_id", required = false)
    java.lang.Integer parentId) {
        try {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.PostComment> postCommentIPage = postCommentService.getCommentPage(pageForm.getPage(), pageForm.getSize(), postId, parentId);
            cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.entity.PostComment> postCommentPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(postCommentIPage);
            return cn.meteor.beyondclouds.core.api.Response.success(postCommentPageDTO);
        } catch (cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }
}