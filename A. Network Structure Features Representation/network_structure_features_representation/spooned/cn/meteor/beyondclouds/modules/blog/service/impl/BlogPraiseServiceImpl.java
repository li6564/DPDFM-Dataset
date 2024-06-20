package cn.meteor.beyondclouds.modules.blog.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
@org.springframework.stereotype.Service
public class BlogPraiseServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.blog.mapper.BlogPraiseMapper, cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> implements cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.blog.mapper.BlogPraiseMapper blogPraiseMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogService(cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService) {
        this.blogService = blogService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogCommentService(cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogPraiseMapper(cn.meteor.beyondclouds.modules.blog.mapper.BlogPraiseMapper blogPraiseMapper) {
        this.blogPraiseMapper = blogPraiseMapper;
    }

    @java.lang.Override
    public void praiseBlog(java.lang.String userId, java.lang.String blogId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException {
        // 1.判断博客是否存在
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(blogId);
        if (null == blog) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.BLOG_NOT_FOUND);
        }
        // 2.判断该用户是否已经点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> blogPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogPraiseQueryWrapper.eq("user_id", userId);
        blogPraiseQueryWrapper.eq("target_id", blogId);
        blogPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.blog.entity.BlogPraise blogPraise = getOne(blogPraiseQueryWrapper);
        if (null != blogPraise) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseErrorCode.BLOG_PRAISE_EXIST);
        }
        // 3.保存点赞
        blogPraise = new cn.meteor.beyondclouds.modules.blog.entity.BlogPraise();
        blogPraise.setTargetId(blogId);
        blogPraise.setTargetType(cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_PRAISE.getPraiseType());
        blogPraise.setUserId(userId);
        save(blogPraise);
        // 4.更新博客获赞数量
        blog.setPraiseNum(blog.getPraiseNum() + 1);
        blogService.updateById(blog);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_PRAISE, blogPraise.getPraiseId()));
    }

    @java.lang.Override
    public void deleteBlogPraise(java.lang.String userId, java.lang.String blogId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException {
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(blogId);
        if (null == blog) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.BLOG_NOT_FOUND);
        }
        // 1.判断是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("target_id", blogId);
        queryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.blog.entity.BlogPraise blogPraise = getOne(queryWrapper);
        if (null == blogPraise) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseErrorCode.NO_PRAISE_FOUND);
        }
        // 2.取消点赞
        remove(queryWrapper);
        // 4.更新博客获赞数量
        blog.setPraiseNum(blog.getPraiseNum() - 1);
        blogService.updateById(blog);
    }

    @java.lang.Override
    public void praiseBlogComment(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException {
        // 1.判断评论是否存在
        cn.meteor.beyondclouds.modules.blog.entity.BlogComment blogComment = blogCommentService.getById(commentId);
        if (null == blogComment) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCommentErrorCode.COMMENT_NOT_FOUND);
        }
        // 2.判断该用户是否已经点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> blogPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogPraiseQueryWrapper.eq("user_id", currentUserId);
        blogPraiseQueryWrapper.eq("target_id", commentId);
        blogPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_COMMENT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.blog.entity.BlogPraise blogPraise = getOne(blogPraiseQueryWrapper);
        if (null != blogPraise) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseErrorCode.BLOG_PRAISE_EXIST);
        }
        // 3.保存点赞
        blogPraise = new cn.meteor.beyondclouds.modules.blog.entity.BlogPraise();
        blogPraise.setTargetId(commentId);
        blogPraise.setTargetType(cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_COMMENT_PRAISE.getPraiseType());
        blogPraise.setUserId(currentUserId);
        save(blogPraise);
    }

    @java.lang.Override
    public void deleteBlogCommentPraise(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException {
        // 1.判断是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("user_id", currentUserId);
        queryWrapper.eq("target_id", commentId);
        queryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_COMMENT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.blog.entity.BlogPraise blogPraise = getOne(queryWrapper);
        if (null == blogPraise) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogPraiseServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseErrorCode.NO_PRAISE_FOUND);
        }
        // 2.取消点赞
        remove(queryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getBlogPraises(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String blogId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> praiseUserDTOPage = blogPraiseMapper.selectPraisePage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page(pageNumber, pageSize), blogId, cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(praiseUserDTOPage, pageDTO);
        pageDTO.setDataList(praiseUserDTOPage.getRecords());
        return pageDTO;
    }
}