package cn.meteor.beyondclouds.modules.blog.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 博客类别表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IBlogCategoryService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory> {
    /**
     * 获取博客分类列表
     *
     * @return  * @param categoryId
     */
    java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory> getBlogCategory(java.lang.Integer categoryId);
}