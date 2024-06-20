package cn.meteor.beyondclouds.modules.blog.service;
import cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 博客表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IBlogService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.blog.entity.Blog> {
    /**
     * 发布博客
     *
     * @param blog
     * @param content
     * @param contentHtml
     * @param topicIds
     * @param tagIds
     */
    void publishBlog(cn.meteor.beyondclouds.modules.blog.entity.Blog blog, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> topicIds, java.util.List<java.lang.String> tagIds) throws cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException;

    /**
     * 删除博客
     *
     * @param userId
     * @param BlogId
     */
    void deleteBlog(java.lang.String userId, java.lang.String BlogId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException;

    /**
     * 获取博客详情
     *
     * @param blogId
     * @param subject
     * @param updateViewNum
     * @return  * @throws BlogServiceException
     */
    cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO getBlog(java.lang.String blogId, cn.meteor.beyondclouds.core.authentication.Subject subject, boolean updateViewNum) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException;

    /**
     * 博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @param categoryId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer categoryId);

    /**
     * 个人博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getUserBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 我的博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getMyBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 更新博客
     *
     * @param blog
     * @param content
     * @param contentHtml
     * @param topicIds
     * @param tagIds
     */
    void updateBlog(cn.meteor.beyondclouds.modules.blog.entity.Blog blog, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> topicIds, java.util.List<java.lang.String> tagIds) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException;

    /**
     * 热门博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getHotBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 根据标签Id查询博客
     *
     * @param pageNumber
     * @param pageSize
     * @param tagId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getBlogPageByTagId(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String tagId);

    /**
     * 相关博客
     *
     * @param page
     * @param size
     * @param blogId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getRelatedBlogPage(java.lang.Integer page, java.lang.Integer size, java.lang.String blogId);

    /**
     * 更新博客的用户昵称
     *
     * @param userId
     */
    void updateBlogUserNick(java.lang.String userId);

    /**
     * 更新博客的用户头像
     *
     * @param userId
     */
    void updateBlogUserAvatar(java.lang.String userId);

    /**
     * 获取用户所有博客的浏览量
     *
     * @param operatorId
     * @return  */
    long allBlogViewCount(java.lang.String operatorId);
}