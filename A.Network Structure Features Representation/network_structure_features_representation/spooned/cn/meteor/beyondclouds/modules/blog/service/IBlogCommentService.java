package cn.meteor.beyondclouds.modules.blog.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 博客评论表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IBlogCommentService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> {
    /**
     * 创建评论
     *
     * @param blogId
     * @param parentId
     * @param comment
     * @param userId
     */
    void createComment(java.lang.String blogId, java.lang.Integer parentId, java.lang.String comment, java.lang.String userId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException;

    /**
     * 删除评论
     *
     * @param commentId
     * @param userId
     */
    void deleteComment(java.lang.Integer commentId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException;

    /**
     * 获取评论分页
     *
     * @param pageNumber
     * @param size
     * @param blogId
     * @param parentId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> getCommentPage(java.lang.Integer pageNumber, java.lang.Integer size, java.lang.String blogId, java.lang.Integer parentId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException;

    /**
     * 更新博客评论的用户昵称
     *
     * @param userId
     */
    void updateBlogCommentUserNick(java.lang.String userId);

    /**
     * 更新博客评论的用户头像
     *
     * @param userId
     */
    void updateBlogCommentUserAvatar(java.lang.String userId);
}