package cn.meteor.beyondclouds.modules.post.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author gaoTong
 * @date 2020/2/2 9:40
 */
@io.swagger.annotations.Api(tags = "动态API")
@cn.meteor.beyondclouds.modules.post.api.RequestMapping("/api")
@cn.meteor.beyondclouds.modules.post.api.RestController
public class PostApi {
    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostService(cn.meteor.beyondclouds.modules.post.service.IPostService postService) {
        this.postService = postService;
    }

    /**
     * 发布动态
     *
     * @param postForm
     * @param result
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("发布动态")
    @cn.meteor.beyondclouds.modules.post.api.PostMapping("/post")
    public cn.meteor.beyondclouds.core.api.Response publishPost(@cn.meteor.beyondclouds.modules.post.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.post.form.PostForm postForm, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        // 对图片进行类型转换
        cn.meteor.beyondclouds.modules.post.entity.Post post = new cn.meteor.beyondclouds.modules.post.entity.Post();
        org.springframework.beans.BeanUtils.copyProperties(postForm, post);
        if (!org.springframework.util.StringUtils.isEmpty(postForm.getPictures())) {
            post.setPictures(postForm.getPictures().stream().map(java.lang.String::valueOf).collect(java.util.stream.Collectors.joining(",")));
        }
        post.setUserId(((java.lang.String) (subject.getId())));
        // 发布动态
        try {
            postService.publishPost(post);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 删除动态
     *
     * @param postId
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("删除动态")
    @cn.meteor.beyondclouds.modules.post.api.DeleteMapping("/post/{postId}")
    public cn.meteor.beyondclouds.core.api.Response deletePost(@cn.meteor.beyondclouds.modules.post.api.PathVariable("postId")
    java.lang.String postId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 删除动态
        try {
            postService.deletePost(postId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.post.exception.PostServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 动态列表
     *
     * @param pageForm
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("动态列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/posts")
    public cn.meteor.beyondclouds.core.api.Response<?> getPostPage(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.post.api.RequestParam(value = "type", required = false)
    java.lang.Integer type) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        if ((null != type) && (!java.util.List.of(0, 1, 2).contains(type))) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.TYPE_ERROR);
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postPage = postService.getPostPage(pageForm.getPage(), pageForm.getSize(), type);
        return cn.meteor.beyondclouds.core.api.Response.success(postPage);
    }

    /**
     * 我的动态列表
     *
     * @param pageForm
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("我的动态列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/my/posts")
    public cn.meteor.beyondclouds.core.api.Response<?> getMyPosts(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postPage = postService.getUserPostPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        return cn.meteor.beyondclouds.core.api.Response.success(postPage);
    }

    /**
     * 获取他人动态列表
     *
     * @param userId
     * @param pageForm
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("他人动态列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/user/{userId}/posts")
    public cn.meteor.beyondclouds.core.api.Response<?> getOtherPosts(@cn.meteor.beyondclouds.modules.post.api.PathVariable("userId")
    java.lang.String userId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postPage = postService.getUserPostPage(pageForm.getPage(), pageForm.getSize(), userId);
        return cn.meteor.beyondclouds.core.api.Response.success(postPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @io.swagger.annotations.ApiOperation("获取我关注的用户的动态列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/posts/followed")
    public cn.meteor.beyondclouds.core.api.Response<?> getMyFollowdePosts(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postPage = postService.getFollowedPostPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        return cn.meteor.beyondclouds.core.api.Response.success(postPage);
    }

    /**
     * 推荐动态
     *
     * @param pageForm
     * @param bindingResult
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("推荐动态列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/post/recommends")
    public cn.meteor.beyondclouds.core.api.Response<?> recommendPosts(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postDTOPage = postService.getRecommendPosts(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(postDTOPage);
    }

    /**
     * 话题下的推荐动态列表
     *
     * @param pageForm
     * @param bindingResult
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("话题下的推荐动态列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/topic/{topicId}/post/recommends")
    public cn.meteor.beyondclouds.core.api.Response<?> recommendPostsInTopic(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.post.api.PathVariable("topicId")
    java.lang.String topicId, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postDTOPage = postService.getRecommendPostsInTopic(topicId, pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(postDTOPage);
    }
}