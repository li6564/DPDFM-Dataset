package cn.meteor.beyondclouds.modules.post.api;
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
@io.swagger.annotations.Api(tags = "动态点赞API")
@cn.meteor.beyondclouds.modules.post.api.RestController
@cn.meteor.beyondclouds.modules.post.api.RequestMapping("/api")
public class PostPraiseApi {
    private cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostPraiseService(cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService) {
        this.postPraiseService = postPraiseService;
    }

    @io.swagger.annotations.ApiOperation("动态点赞")
    @cn.meteor.beyondclouds.modules.post.api.PostMapping("/post/{postId}/praise")
    public cn.meteor.beyondclouds.core.api.Response praise(@cn.meteor.beyondclouds.modules.post.api.PathVariable("postId")
    java.lang.String postId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            postPraiseService.postPraise(currentUserId, postId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.post.exception.PostServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消点赞")
    @cn.meteor.beyondclouds.modules.post.api.DeleteMapping("/post/{postId}/praise")
    public cn.meteor.beyondclouds.core.api.Response deletePraise(@cn.meteor.beyondclouds.modules.post.api.PathVariable("postId")
    java.lang.String postId) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        try {
            postPraiseService.delPostPraise(currentUserId, postId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.post.exception.PostServiceException e) {
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("动态点赞列表")
    @cn.meteor.beyondclouds.modules.post.api.GetMapping("/post/{postId}/praises")
    public cn.meteor.beyondclouds.core.api.Response getPraises(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.post.api.PathVariable("postId")
    java.lang.String postId) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = postPraiseService.getPostPraises(pageForm.getPage(), pageForm.getSize(), postId);
        return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
    }
}