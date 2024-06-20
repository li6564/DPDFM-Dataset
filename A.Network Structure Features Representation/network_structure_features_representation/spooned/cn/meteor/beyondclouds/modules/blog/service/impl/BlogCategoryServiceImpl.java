package cn.meteor.beyondclouds.modules.blog.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 博客类别表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class BlogCategoryServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.blog.mapper.BlogCategoryMapper, cn.meteor.beyondclouds.modules.blog.entity.BlogCategory> implements cn.meteor.beyondclouds.modules.blog.service.IBlogCategoryService {
    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory> getBlogCategory(java.lang.Integer categoryId) {
        // 只获取第一层分类
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory> blogCategoryQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        if (null == categoryId) {
            blogCategoryQueryWrapper.isNull(true, "parent_id");
        } else {
            blogCategoryQueryWrapper.eq("parent_id", categoryId);
        }
        blogCategoryQueryWrapper.orderByDesc("priority");
        blogCategoryQueryWrapper.orderByAsc("category_id");
        java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogCategory> blogCategoryList = list(blogCategoryQueryWrapper);
        return blogCategoryList;
    }
}