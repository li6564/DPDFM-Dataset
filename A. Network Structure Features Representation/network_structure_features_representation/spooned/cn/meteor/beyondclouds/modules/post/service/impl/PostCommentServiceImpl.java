package cn.meteor.beyondclouds.modules.post.service.impl;
import cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
/**
 * <p>
 * 动态评论表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class PostCommentServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.post.mapper.PostCommentMapper, cn.meteor.beyondclouds.modules.post.entity.PostComment> implements cn.meteor.beyondclouds.modules.post.service.IPostCommentService {
    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostService(cn.meteor.beyondclouds.modules.post.service.IPostService postService) {
        this.postService = postService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    /**
     * 发布评论
     *
     * @param postId
     * @param parentId
     * @param comment
     * @param userId
     */
    @java.lang.Override
    public void createComment(java.lang.String postId, java.lang.Integer parentId, java.lang.String comment, java.lang.String userId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException, cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException {
        // 1.判断动态是否存在
        cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(postId);
        if (null == post) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.POST_NOT_FOUND);
        }
        // 2.如果有parentId判断父评论是否存在
        cn.meteor.beyondclouds.modules.post.entity.PostComment parentComment = null;
        if (null != parentId) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostComment> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            queryWrapper.eq("post_id", postId);
            queryWrapper.eq("comment_id", parentId);
            parentComment = getOne(queryWrapper);
            if (null == parentComment) {
                throw new cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException(cn.meteor.beyondclouds.modules.post.enums.PostCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
            }
        }
        // 3.保存评论
        // 获取评论者用户信息
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        cn.meteor.beyondclouds.modules.post.entity.PostComment postComment = new cn.meteor.beyondclouds.modules.post.entity.PostComment();
        postComment.setParentId(parentId);
        postComment.setComment(comment);
        postComment.setUserId(userId);
        postComment.setUserNick(user.getNickName());
        postComment.setUserAvatar(user.getUserAvatar());
        postComment.setPostId(postId);
        save(postComment);
        // 更新深度和路径
        if (null == parentComment) {
            // 一级评论
            postComment.setDepth(0);
            postComment.setThread("/" + postComment.getCommentId());
        } else {
            // 子级评论
            postComment.setDepth(parentComment.getDepth() + 1);
            postComment.setThread((parentComment.getThread() + "/") + postComment.getCommentId());
        }
        updateById(postComment);
        // 更新动态的评论数量
        post.setCommentNumber(post.getCommentNumber() + 1);
        postService.updateById(post);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST_COMMENT, postComment.getCommentId()));
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @param userId
     * @throws PostCommentServiceException
     */
    @java.lang.Override
    public void deleteComment(java.lang.Integer commentId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException {
        org.springframework.util.Assert.notNull(commentId, "commentId must not be null");
        org.springframework.util.Assert.notNull(userId, "userId must not be null");
        // 1.判断是不是自己的评论
        cn.meteor.beyondclouds.modules.post.entity.PostComment postComment = getById(commentId);
        if (null == postComment) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException(cn.meteor.beyondclouds.modules.post.enums.PostCommentErrorCode.COMMENT_NOT_FOUND);
        }
        // 2.判断是不是自己的评论
        if (!userId.equals(postComment.getUserId())) {
            // 3.判断是不是自己的博客
            cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(postComment.getPostId());
            if (!post.getUserId().equals(userId)) {
                throw new cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException(cn.meteor.beyondclouds.modules.post.enums.PostCommentErrorCode.NO_DELETE_PRIVILEGES);
            }
        }
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper postCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        postCommentQueryWrapper.like("thread", postComment.getThread());
        int removeCount = count(postCommentQueryWrapper);
        remove(postCommentQueryWrapper);
        // 更新动态的评论数量
        cn.meteor.beyondclouds.modules.post.entity.Post post = postService.getById(postComment.getPostId());
        post.setCommentNumber(post.getCommentNumber() - removeCount);
        postService.updateById(post);
    }

    /**
     * 评论列表
     *
     * @param pageNumber
     * @param size
     * @param postId
     * @param parentId
     * @return  * @throws PostCommentServiceException
     */
    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.PostComment> getCommentPage(java.lang.Integer pageNumber, java.lang.Integer size, java.lang.String postId, java.lang.Integer parentId) throws cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException {
        org.springframework.util.Assert.notNull(postId, "postId must not null");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.PostComment> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(pageNumber, size);
        // 1.如果parentId为null，则只获取一级评论
        if (null == parentId) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper postCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
            postCommentQueryWrapper.eq("post_id", postId);
            postCommentQueryWrapper.eq("depth", 0);
            postCommentQueryWrapper.orderByDesc("create_time");
            return page(page, postCommentQueryWrapper);
        }
        // 2.如果parentId不为null，则获取其子查询
        // 判断parent是否存在
        cn.meteor.beyondclouds.modules.post.entity.PostComment parentPostComment = getById(parentId);
        if (null == parentPostComment) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostCommentServiceException(cn.meteor.beyondclouds.modules.post.enums.PostCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
        }
        // 查询子评论
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper postCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        postCommentQueryWrapper.eq("parent_id", parentId);
        return page(page, postCommentQueryWrapper);
    }

    /**
     * 更新动态评论里的用户头像
     *
     * @param userId
     */
    @java.lang.Override
    public void updatePostUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.post.entity.PostComment> postUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            postUpdateWrapper.eq("user_id", userId);
            postUpdateWrapper.set("user_avatar", user.getUserAvatar());
            update(postUpdateWrapper);
        }
    }

    @java.lang.Override
    public void updatePostUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.post.entity.PostComment> postUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            postUpdateWrapper.eq("user_id", userId);
            postUpdateWrapper.set("user_nick", user.getNickName());
            update(postUpdateWrapper);
        }
    }
}