package cn.meteor.beyondclouds.modules.blog.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
public interface IBlogPraiseService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> {
    /**
     * 博客点赞
     *
     * @param userId
     * @param targetId
     */
    void praiseBlog(java.lang.String userId, java.lang.String targetId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException;

    /**
     * 取消博客点赞
     *
     * @param userId
     * @param blogId
     */
    void deleteBlogPraise(java.lang.String userId, java.lang.String blogId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException;

    /**
     * 博客评论点赞
     *
     * @param currentUserId
     * @param commentId
     */
    void praiseBlogComment(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException;

    /**
     * 删除博客评论点赞
     *
     * @param currentUserId
     * @param commentId
     */
    void deleteBlogCommentPraise(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException;

    /**
     * 获取博客点赞列表
     *
     * @param pageNumver
     * @param pageSize
     * @param blogId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getBlogPraises(java.lang.Integer pageNumver, java.lang.Integer pageSize, java.lang.String blogId);
}