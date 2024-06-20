package cn.meteor.beyondclouds.modules.blog.service.impl;
import cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException;
import cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
/**
 * <p>
 * 博客评论表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class BlogCommentServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.blog.mapper.BlogCommentMapper, cn.meteor.beyondclouds.modules.blog.entity.BlogComment> implements cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogService(cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService) {
        this.blogService = blogService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogPraiseService(cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService) {
        this.blogPraiseService = blogPraiseService;
    }

    /**
     * 发布评论
     *
     * @param blogId
     * @param parentId
     * @param comment
     * @param userId
     * @throws BlogServiceException
     * @throws BlogCommentServiceException
     */
    @java.lang.Override
    public void createComment(java.lang.String blogId, java.lang.Integer parentId, java.lang.String comment, java.lang.String userId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException {
        // 判断博客是否存在，并且有评论权限
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(blogId);
        if (null != blog) {
            if (!blog.getAllowComment()) {
                throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCommentErrorCode.NO_COMMENT_PRIVILEGES);
            }
        } else {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.BLOG_NOT_FOUND);
        }
        // 定义路径
        java.lang.String thread;
        int depth;
        // 1.判断为第几级评论
        if (parentId == null) {
            depth = 0;
        } else {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            queryWrapper.eq("comment_id", parentId).eq("blog_id", blogId);
            cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogCommentParent = getOne(queryWrapper);
            if (null == blogCommentParent) {
                throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
            }
            depth = blogCommentParent.getDepth() + 1;
        }
        // 2.创建评论
        // 查找用户
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogComment = new cn.meteor.beyondclouds.modules.blog.entity.BlogComment();
        blogComment.setBlogId(blogId);
        blogComment.setComment(comment);
        blogComment.setDepth(depth);
        blogComment.setParentId(parentId);
        blogComment.setUserId(userId);
        blogComment.setUserNick(user.getNickName());
        blogComment.setUserAvatar(user.getUserAvatar());
        save(blogComment);
        // 3.更新评论次数
        if (null == blog.getCommentNumber()) {
            blog.setCommentNumber(1);
        } else {
            blog.setCommentNumber(blog.getCommentNumber() + 1);
        }
        blogService.updateById(blog);
        // 4.查找上一层目录
        if (blogComment.getParentId() == null) {
            thread = "/" + blogComment.getCommentId();
        } else {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            queryWrapper.eq("comment_id", blogComment.getParentId());
            // 重新创建对象 存储上一级目录
            cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogCommentPatent = getOne(queryWrapper);
            thread = (blogCommentPatent.getThread() + "/") + blogComment.getCommentId();
        }
        blogComment.setThread(thread);
        // 5.更新路径信息
        updateById(blogComment);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_COMMENT, blogComment.getCommentId()));
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @param userId
     * @throws BlogCommentServiceException
     */
    @java.lang.Override
    public void deleteComment(java.lang.Integer commentId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException {
        org.springframework.util.Assert.notNull(commentId, "commentId must not be null");
        org.springframework.util.Assert.notNull(userId, "userId must not be null");
        // 1.查找评论是否存在
        cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogComment = getById(commentId);
        if (null == blogComment) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCommentErrorCode.COMMENT_NOT_FOUND);
        }
        // 2.判断是不是自己的评论
        if (!userId.equals(blogComment.getUserId())) {
            // 3.如果不是自己的评论看是否是自己的博客
            cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(blogComment.getBlogId());
            if (!blog.getUserId().equals(userId)) {
                throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCommentErrorCode.NO_DELETE_PRIVILEGES);
            }
        }
        // 4.删除评论和对评论的赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        blogCommentQueryWrapper.like("thread", blogComment.getThread());
        java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentList = list(blogCommentQueryWrapper);
        java.util.List<java.lang.Integer> commentIds = blogCommentList.stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogComment::getCommentId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(commentIds)) {
            // 删点赞
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> blogPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            blogPraiseQueryWrapper.in("target_id", commentIds);
            blogPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_COMMENT_PRAISE.getPraiseType());
            blogPraiseService.remove(blogPraiseQueryWrapper);
            // 删评论
            remove(blogCommentQueryWrapper);
        }
    }

    /**
     * 评论列表
     *
     * @param pageNumber
     * @param size
     * @param blogId
     * @param parentId
     * @return  * @throws BlogCommentServiceException
     */
    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> getCommentPage(java.lang.Integer pageNumber, java.lang.Integer size, java.lang.String blogId, java.lang.Integer parentId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException {
        org.springframework.util.Assert.notNull(blogId, "blogId must not be null");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, size);
        // 如果parentId为null，则只获取一级评论
        if (null == parentId) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
            blogCommentQueryWrapper.eq("blog_id", blogId);
            blogCommentQueryWrapper.eq("depth", 0);
            blogCommentQueryWrapper.orderByDesc("create_time");
            return page(page, blogCommentQueryWrapper);
        }
        // 如果parent不为空，则获取其子评论
        cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogComment = getById(parentId);
        if (null == blogComment) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCommentServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
        }
        // 根据parentId查出其子评论
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        blogCommentQueryWrapper.eq("parent_id", parentId);
        return page(page, blogCommentQueryWrapper);
    }

    @java.lang.Override
    public void updateBlogCommentUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userNick = user.getNickName();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentUserNickUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper();
            blogCommentUserNickUpdateWrapper.eq("user_id", userId);
            blogCommentUserNickUpdateWrapper.set("user_nick", userNick);
            update(blogCommentUserNickUpdateWrapper);
        }
    }

    @java.lang.Override
    public void updateBlogCommentUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userAvatar = user.getUserAvatar();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentUserAvatarUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            blogCommentUserAvatarUpdateWrapper.eq("user_id", userId);
            blogCommentUserAvatarUpdateWrapper.set("user_avatar", userAvatar);
            update(blogCommentUserAvatarUpdateWrapper);
        }
    }
}