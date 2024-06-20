package cn.meteor.beyondclouds.modules.blog.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 博客标签表，用来记录博客里面引用了哪些标签 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IBlogTagService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> {
    /**
     * 根据标签获取博客ID
     *
     * @param pageNumber
     * @param pageSize
     * @param tagId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> getBlogIdByTagId(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String tagId);

    /**
     * 根据博客ID获取相关的博客ID
     *
     * @param page
     * @param tagIds
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> getRelatedBlogIds(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.util.Collection<java.lang.String> tagIds);
}