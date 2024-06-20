package cn.meteor.beyondclouds.modules.post.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 动态评论表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IPostCommentService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.post.entity.PostComment> {
    /**
     * 创建评论
     *
     * @param postId
     * @param parentId
     * @param comment
     * @param userId
     */
    void createComment(java.lang.String postId, java.lang.Integer parentId, java.lang.String comment, java.lang.String userId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException, cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException;

    /**
     * 删除评论
     *
     * @param commentId
     * @param userId
     */
    void deleteComment(java.lang.Integer commentId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException;

    /**
     * 评论列表
     *
     * @param pageNumber
     * @param size
     * @param postId
     * @param parentId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.PostComment> getCommentPage(java.lang.Integer pageNumber, java.lang.Integer size, java.lang.String postId, java.lang.Integer parentId) throws cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException;

    /**
     * 更新动态评论里面的用户头像
     *
     * @param userId
     */
    void updatePostUserAvatar(java.lang.String userId);

    /**
     * 更新动态评论里的用户昵称
     *
     * @param userId
     */
    void updatePostUserNick(java.lang.String userId);
}