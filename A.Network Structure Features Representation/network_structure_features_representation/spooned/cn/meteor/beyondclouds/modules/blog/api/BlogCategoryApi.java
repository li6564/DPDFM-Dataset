package cn.meteor.beyondclouds.modules.blog.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author gaoTong
 * @date 2020/2/12 10:04
 */
@io.swagger.annotations.Api(tags = "博客分类Api")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api")
public class BlogCategoryApi {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogCategoryService blogCategoryService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogCategoryService(cn.meteor.beyondclouds.modules.blog.service.IBlogCategoryService blogCategoryService) {
        this.blogCategoryService = blogCategoryService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取博客分类")
    @org.springframework.web.bind.annotation.GetMapping("/blog/categories")
    public cn.meteor.beyondclouds.core.api.Response<java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory>> getBlogCategories() {
        return cn.meteor.beyondclouds.core.api.Response.success(blogCategoryService.getBlogCategory(null));
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取博客子分类")
    @org.springframework.web.bind.annotation.GetMapping("/blog/{categoryId}/categories")
    public cn.meteor.beyondclouds.core.api.Response<java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory>> getBlogSubCategories(@org.springframework.web.bind.annotation.PathVariable("categoryId")
    java.lang.Integer categoryId) {
        return cn.meteor.beyondclouds.core.api.Response.success(blogCategoryService.getBlogCategory(categoryId));
    }
}