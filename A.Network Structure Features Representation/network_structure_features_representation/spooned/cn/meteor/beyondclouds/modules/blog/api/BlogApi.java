package cn.meteor.beyondclouds.modules.blog.api;
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
 * @author gaoTong
 * @date 2020/1/31 9:27
 */
@io.swagger.annotations.Api(tags = "博客API")
@cn.meteor.beyondclouds.modules.blog.api.RestController
@cn.meteor.beyondclouds.modules.blog.api.RequestMapping("/api")
public class BlogApi {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogService(cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService) {
        this.blogService = blogService;
    }

    @io.swagger.annotations.ApiOperation("发布博客")
    @cn.meteor.beyondclouds.modules.blog.api.PostMapping("/blog")
    public cn.meteor.beyondclouds.core.api.Response publishBlog(@cn.meteor.beyondclouds.modules.blog.api.RequestBody
    @org.springframework.validation.annotation.Validated(cn.meteor.beyondclouds.core.validation.groups.InsertGroup.class)
    cn.meteor.beyondclouds.modules.blog.form.BlogForm blogForm, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        if ((null != blogForm.getCover()) && org.apache.commons.lang3.StringUtils.isBlank(blogForm.getCover())) {
            blogForm.setCover(null);
        }
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = new cn.meteor.beyondclouds.modules.blog.entity.Blog();
        org.springframework.beans.BeanUtils.copyProperties(blogForm, blog);
        blog.setUserId(((java.lang.String) (subject.getId())));
        try {
            blogService.publishBlog(blog, blogForm.getContent(), blogForm.getContentHtml(), blogForm.getTopicIds(), blogForm.getTagIds());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("删除博客")
    @cn.meteor.beyondclouds.modules.blog.api.DeleteMapping("/blog/{blogId}")
    public cn.meteor.beyondclouds.core.api.Response deleteBlog(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            blogService.deleteBlog(((java.lang.String) (subject.getId())), blogId);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("博客详情")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/blog/{blogId}")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO> getBlog(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(paramName = "blogId", type = cn.meteor.beyondclouds.core.flow.ParamType.BLOG)
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        boolean updateViewNum = false;
        if (cn.meteor.beyondclouds.util.AccessInfoUtils.hasFieldInfo(accessInfo)) {
            updateViewNum = accessInfo.getFieldVisitCount() == 0;
        }
        try {
            cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO blogDetail = blogService.getBlog(blogId, subject, updateViewNum);
            return cn.meteor.beyondclouds.core.api.Response.success(blogDetail);
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.PreventDuplicate
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("博客列表")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/blogs")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog>> getBlogs(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.blog.api.RequestParam(value = "categoryId", required = false)
    java.lang.Integer categoryId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogService.getBlogPage(pageForm.getPage(), pageForm.getSize(), categoryId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blogPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blogPageDTO);
    }

    /**
     * 我的博客列表
     *
     * @param pageForm
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("我的博客列表")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/my/blogs")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog>> getMyBlogs(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogService.getMyBlogPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blogPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blogPageDTO);
    }

    /**
     * 他人博客列表
     *
     * @param pageForm
     * @param userId
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("他人博客列表")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/user/{userId}/blogs")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog>> getOtherBlogs(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.blog.api.PathVariable("userId")
    java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogService.getUserBlogPage(pageForm.getPage(), pageForm.getSize(), userId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blogPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blogPageDTO);
    }

    @io.swagger.annotations.ApiOperation("修改博客")
    @cn.meteor.beyondclouds.modules.blog.api.PutMapping("/blog/{blogId}")
    public cn.meteor.beyondclouds.core.api.Response updateBlog(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId, @cn.meteor.beyondclouds.modules.blog.api.RequestBody
    @org.springframework.validation.annotation.Validated(cn.meteor.beyondclouds.core.validation.groups.UpdateGroup.class)
    cn.meteor.beyondclouds.modules.blog.form.BlogForm blogForm, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = new cn.meteor.beyondclouds.modules.blog.entity.Blog();
        org.springframework.beans.BeanUtils.copyProperties(blogForm, blog);
        blog.setUserId(((java.lang.String) (subject.getId())));
        blog.setBlogId(blogId);
        // 更新项目
        try {
            blogService.updateBlog(blog, blogForm.getContent(), blogForm.getContentHtml(), blogForm.getTopicIds(), blogForm.getTagIds());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException | cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门博客")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/blog/hots")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog>> getHotBlogs(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogService.getHotBlogPage(pageForm.getPage(), pageForm.getSize());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blogPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blogPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("根据标签查找博客列表")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/tag/{tagId}/blogs")
    public cn.meteor.beyondclouds.core.api.Response<?> getBlogsByTagId(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("tagId")
    java.lang.String tagId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogService.getBlogPageByTagId(pageForm.getPage(), pageForm.getSize(), tagId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blogPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blogPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("相关博客")
    @cn.meteor.beyondclouds.modules.blog.api.GetMapping("/blog/{blogId}/recommends")
    public cn.meteor.beyondclouds.core.api.Response<?> getRelatedBlogs(@cn.meteor.beyondclouds.modules.blog.api.PathVariable("blogId")
    java.lang.String blogId, @javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogService.getRelatedBlogPage(pageForm.getPage(), pageForm.getSize(), blogId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blogPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blogPageDTO);
    }
}