package cn.meteor.beyondclouds.modules.blog.service.impl;
import cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException;
import cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
/**
 * <p>
 * 博客表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@lombok.extern.java.Log
@org.springframework.stereotype.Service
public class BlogServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.blog.mapper.BlogMapper, cn.meteor.beyondclouds.modules.blog.entity.Blog> implements cn.meteor.beyondclouds.modules.blog.service.IBlogService {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogTagService blogTagService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogExtService blogExtService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogCategoryService blogCategoryService;

    private cn.meteor.beyondclouds.modules.blog.mapper.BlogMapper blogMapper;

    private cn.meteor.beyondclouds.modules.tag.service.ITagService tagService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public BlogServiceImpl(cn.meteor.beyondclouds.modules.blog.service.IBlogTagService blogTagService, cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService, cn.meteor.beyondclouds.modules.blog.service.IBlogExtService blogExtService, cn.meteor.beyondclouds.modules.blog.service.IBlogCategoryService blogCategoryService, cn.meteor.beyondclouds.modules.blog.mapper.BlogMapper blogMapper, cn.meteor.beyondclouds.modules.tag.service.ITagService tagService) {
        this.blogTagService = blogTagService;
        this.topicReferenceService = topicReferenceService;
        this.blogExtService = blogExtService;
        this.blogCategoryService = blogCategoryService;
        this.blogMapper = blogMapper;
        this.tagService = tagService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setTopicService(cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService) {
        this.topicService = topicService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogCommentService(cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogPraiseService(cn.meteor.beyondclouds.modules.blog.service.IBlogPraiseService blogPraiseService) {
        this.blogPraiseService = blogPraiseService;
    }

    /**
     * 发布博客
     *
     * @param blog
     * @param content
     * @param contentHtml
     * @param topicIds
     * @param tagIds
     * @throws BlogCategoryServiceException
     */
    @java.lang.Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    public void publishBlog(cn.meteor.beyondclouds.modules.blog.entity.Blog blog, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> topicIds, java.util.List<java.lang.String> tagIds) throws cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException {
        // 如果为不可查看则默认为不可评论和转载
        if (0 == blog.getViewPrivileges()) {
            blog.setAllowComment(false);
            blog.setAllowForward(false);
        }
        // 如果没有手动添加摘要则自动从内容里摘取20字
        if (null == blog.getBlogAbstract()) {
            blog.setBlogAbstract(cn.meteor.beyondclouds.util.AbstractUtils.extractWithoutHtml(content, 20));
        }
        // 1.判断博客类型是否存在
        cn.meteor.beyondclouds.modules.blog.entity.BlogCategory blogCategory = blogCategoryService.getById(blog.getCategoryId());
        if (null == blogCategory) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCategoryErrorCode.INCORRECT_CATEGORY);
        }
        // 获取昵称
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(blog.getUserId());
        blog.setUserNick(user.getNickName());
        // 2.保存博客
        blog.setCategory(blogCategory.getCategory());
        save(blog);
        // 3.存入内容
        cn.meteor.beyondclouds.modules.blog.entity.BlogExt blogExt = new cn.meteor.beyondclouds.modules.blog.entity.BlogExt();
        blogExt.setBlogId(blog.getBlogId());
        blogExt.setContent(content);
        blogExt.setContentHtml(contentHtml);
        blogExtService.save(blogExt);
        // 4.更新标签和话题的引用
        updateTopicAndTagReference(user.getUserId(), topicIds, tagIds, blog.getBlogId(), false);
        // 5.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG, blog.getBlogId()));
    }

    /**
     * 删除博客
     *
     * @param userId
     * @param blogId
     * @throws BlogServiceException
     */
    @java.lang.Override
    public void deleteBlog(java.lang.String userId, java.lang.String blogId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException {
        // 1.判断要删除的博客是否是当前用户所有
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogQueryWrapper.eq("user_id", userId).eq("blog_id", blogId).eq("status", 0);
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = getOne(blogQueryWrapper);
        // 1.判断当前用户是否发表过该博客
        if (blog == null) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.USER_BLOG_NOT_FOUND);
        }
        // 2.删除博客的所有评论和博客评论的点赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> queryWrapperComment = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapperComment.eq("blog_id", blogId);
        java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogComment> blogCommentList = blogCommentService.list(queryWrapperComment);
        java.util.List<java.lang.Integer> blogCommentIds = blogCommentList.stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogComment::getCommentId).collect(java.util.stream.Collectors.toList());
        // 只有博客有评论的时候才删
        if (!org.springframework.util.CollectionUtils.isEmpty(blogCommentIds)) {
            // 删点赞
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> blogPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            blogPraiseQueryWrapper.in("target_id", blogCommentIds);
            blogPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_COMMENT_PRAISE.getPraiseType());
            blogPraiseService.remove(blogPraiseQueryWrapper);
            // 删评论
            blogCommentService.remove(queryWrapperComment);
        }
        // 3.删除博客内容
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogExt> queryWrapperExt = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapperExt.eq("blog_id", blogId);
        blogExtService.remove(queryWrapperExt);
        // 4.删除博客的点赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> blogPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogPraiseQueryWrapper.eq("target_id", blogId);
        blogPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_PRAISE.getPraiseType());
        blogPraiseService.remove(blogPraiseQueryWrapper);
        // 5.删除博客标签
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> queryWrapperTag = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapperTag.eq("blog_id", blogId);
        // 引用标签数量减一
        java.util.List<java.lang.String> tagIds = blogTagService.list(queryWrapperTag).stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogTag::getTagId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            tagService.decreaseReferenceCountBatch(tagIds, 1);
        }
        blogTagService.remove(queryWrapperTag);
        // 6.删除博客引用的话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> queryWrapperTopic = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapperTopic.eq("referencer_id", blogId);
        // 引用话题数量减一
        java.util.List<java.lang.String> topicIds = topicReferenceService.list(queryWrapperTopic).stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicReference::getTopicId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(topicIds)) {
            topicService.decreaseReferenceCountBatch(topicIds, 1);
        }
        topicReferenceService.remove(queryWrapperTopic);
        // 7.删除博客表里的数据
        removeById(blogId);
        // 5.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.deleteMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG, blog.getBlogId()));
    }

    /**
     * 博客详情
     *
     * @param blogId
     * @param updateViewNum
     * @return  * @throws BlogServiceException
     */
    @java.lang.Override
    public cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO getBlog(java.lang.String blogId, cn.meteor.beyondclouds.core.authentication.Subject subject, boolean updateViewNum) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException {
        org.springframework.util.Assert.notNull(subject, "subject must not be null");
        // 1.获取博客
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogQueryWrapper.eq("blog_id", blogId);
        blogQueryWrapper.eq("status", 0);
        cn.meteor.beyondclouds.modules.blog.entity.Blog blog = getOne(blogQueryWrapper);
        if (null == blog) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.BLOG_NOT_FOUND);
        }
        // 如果当前用户没登录或者当前登录的用户不是博客的发布者，则判断博客是否允许别人查看
        if ((!subject.isAuthenticated()) || (!subject.getId().equals(blog.getUserId()))) {
            if (0 == blog.getViewPrivileges()) {
                throw new cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.NO_VIEW_PRIVILEGES);
            }
        }
        // 更新浏览次数
        if (updateViewNum) {
            if (null == blog.getViewNumber()) {
                blog.setViewNumber(1);
            } else {
                blog.setViewNumber(blog.getViewNumber() + 1);
            }
            updateById(blog);
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_VIEW_NUM, blogId));
        }
        // 2.获取项目内容
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogExt> blogExtQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        blogExtQueryWrapper.eq("blog_id", blogId);
        cn.meteor.beyondclouds.modules.blog.entity.BlogExt blogExt = blogExtService.getOne(blogExtQueryWrapper);
        // 3.获取博客里面的标签
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        blogTagQueryWrapper.eq("blog_id", blogId);
        java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagList = blogTagService.list(blogTagQueryWrapper);
        java.util.List<java.lang.String> tagIds = blogTagList.stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogTag::getTagId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            blog.setTags(tagService.listByIds(tagIds));
        } else {
            blog.setTags(java.util.List.of());
        }
        // 4.获取博客引用的话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        topicReferenceQueryWrapper.eq("referencer_id", blogId);
        topicReferenceQueryWrapper.eq("referencer_type", 0);
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceList = topicReferenceService.list(topicReferenceQueryWrapper);
        java.util.List<java.lang.String> topicIds = topicReferenceList.stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicReference::getTopicId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(topicIds)) {
            blog.setTopics(topicService.listByIds(topicIds));
        } else {
            blog.setTopics(java.util.List.of());
        }
        // 装配并返回查询到的数据
        cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO blogDetail = new cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO();
        org.springframework.beans.BeanUtils.copyProperties(blog, blogDetail);
        blogDetail.setContent(blogExt.getContent());
        blogDetail.setContentHtml(blogExt.getContentHtml());
        // 查看当前用户有没有关注博主
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            blogDetail.setFollowedAuthor(userFollowService.hasFollowedUser(blog.getUserId()));
        } else {
            blogDetail.setFollowedAuthor(false);
        }
        // 查看当前用户有没有对该博客点赞
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> blogPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            blogPraiseQueryWrapper.eq("user_id", subject.getId());
            blogPraiseQueryWrapper.eq("target_id", blog.getBlogId());
            blogPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.blog.enums.BlogPraiseType.BLOG_PRAISE.getPraiseType());
            cn.meteor.beyondclouds.modules.blog.entity.BlogPraise blogPraise = blogPraiseService.getOne(blogPraiseQueryWrapper);
            blogDetail.setPraised(null != blogPraise);
        } else {
            blogDetail.setPraised(false);
        }
        return blogDetail;
    }

    /**
     * 博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @param categoryId
     * @return  */
    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer categoryId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogQueryWrapper.eq("b.view_privileges", 1);
        blogQueryWrapper.eq("b.status", 0);
        if (null != categoryId) {
            blogQueryWrapper.eq("b.category_id", categoryId);
            blogQueryWrapper.orderByDesc("b.category_priority");
        } else {
            blogQueryWrapper.orderByDesc("b.priority");
        }
        blogQueryWrapper.orderByDesc("b.create_time");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = blogMapper.selectPageWithTags(page, blogQueryWrapper);
        return blogPage;
    }

    /**
     * 个人博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getUserBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        blogQueryWrapper.eq("b.user_id", userId);
        blogQueryWrapper.eq("b.status", 0);
        blogQueryWrapper.eq("b.view_privileges", 1);
        blogQueryWrapper.orderByDesc("b.create_time");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        return blogMapper.selectPageWithTags(page, blogQueryWrapper);
    }

    /**
     * 我的博客列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getMyBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> myBlogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        myBlogQueryWrapper.eq("b.user_id", userId);
        myBlogQueryWrapper.orderByDesc("b.create_time");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        return blogMapper.selectPageWithTags(page, myBlogQueryWrapper);
    }

    /**
     * 修改博客
     *
     * @param blog
     * @param content
     * @param contentHtml
     * @param topicIds
     * @param tagIds
     * @throws BlogServiceException
     * @throws BlogCategoryServiceException
     */
    @java.lang.Override
    public void updateBlog(cn.meteor.beyondclouds.modules.blog.entity.Blog blog, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> topicIds, java.util.List<java.lang.String> tagIds) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException {
        org.springframework.util.Assert.notNull(blog, "blog must not be null");
        org.springframework.util.Assert.notNull(blog.getBlogId(), "blogId must not be null");
        org.springframework.util.Assert.notNull(blog.getUserId(), "userId must not be null");
        // 1.判断当前用户是否拥有此博客
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogQueryWrapper.eq("blog_id", blog.getBlogId()).eq("user_id", blog.getUserId()).eq("status", 0);
        cn.meteor.beyondclouds.modules.blog.entity.Blog blogInDb = getOne(blogQueryWrapper);
        if (null == blogInDb) {
            throw new cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogErrorCode.USER_BLOG_NOT_FOUND);
        }
        // 判断博客类型是否正确
        cn.meteor.beyondclouds.modules.blog.entity.BlogCategory blogCategory = null;
        if (blog.getCategoryId() != null) {
            blogCategory = blogCategoryService.getById(blog.getCategoryId());
            if (null == blogCategory) {
                throw new cn.meteor.beyondclouds.modules.blog.exception.BlogCategoryServiceException(cn.meteor.beyondclouds.modules.blog.enums.BlogCategoryErrorCode.INCORRECT_CATEGORY);
            }
        }
        // 2.更新博客基本信息
        if (null != blogCategory) {
            blog.setCategory(blogCategory.getCategory());
        }
        updateById(blog);
        // 3.更新博客内容
        if (!org.springframework.util.StringUtils.isEmpty(content)) {
            cn.meteor.beyondclouds.modules.blog.entity.BlogExt blogExt = new cn.meteor.beyondclouds.modules.blog.entity.BlogExt();
            blogExt.setBlogId(blog.getBlogId());
            blogExt.setContent(content);
            blogExt.setContentHtml(contentHtml);
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogExt> blogExtQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
            blogExtQueryWrapper.eq("blog_id", blog.getBlogId());
            blogExtService.update(blogExt, blogExtQueryWrapper);
        }
        // 4.更新标签和话题的引用
        updateTopicAndTagReference(blog.getUserId(), topicIds, tagIds, blog.getBlogId(), true);
        // 5.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG, blog.getBlogId()));
    }

    /**
     * 更新博客里面对标签和话题的引用
     *
     * @param userId
     * @param tagIds
     * @param topicIds
     * @param blogId
     * @param update
     */
    private void updateTopicAndTagReference(java.lang.String userId, java.util.List<java.lang.String> topicIds, java.util.List<java.lang.String> tagIds, java.lang.String blogId, boolean update) {
        // 如果用户传的topicIds为[]并且现在在执行修改博客的操作，就删除该博客以前引用的所有话题
        if (org.springframework.util.CollectionUtils.isEmpty(topicIds) && update) {
            deleteOldTopicReferences(blogId);
        }
        // 如果用户传的tagIds为[]并且现在在执行修改博客的操作，就删除该博客以前引用的所有标签
        if (org.springframework.util.CollectionUtils.isEmpty(tagIds) && update) {
            deleteOldTagReferences(blogId);
        }
        // 1.处理话题引用
        if (!org.springframework.util.CollectionUtils.isEmpty(topicIds)) {
            // 获取要引用的话题，不正确的话题ID会被自动过滤
            java.util.List<java.lang.String> existsTopicIds = topicService.listByIds(topicIds).stream().map(cn.meteor.beyondclouds.modules.topic.entity.Topic::getTopicId).collect(java.util.stream.Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(existsTopicIds)) {
                // 如果现在执行的是修改操作，则删除旧话题引用并更新话题的引用量
                if (update) {
                    deleteOldTopicReferences(blogId);
                }
                java.util.List<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceList = new java.util.ArrayList<>();
                for (java.lang.String topicId : existsTopicIds) {
                    cn.meteor.beyondclouds.modules.topic.entity.TopicReference topicReference = new cn.meteor.beyondclouds.modules.topic.entity.TopicReference();
                    topicReference.setUserId(userId);
                    topicReference.setTopicId(topicId);
                    topicReference.setReferencerId(blogId);
                    topicReference.setReferencerType(cn.meteor.beyondclouds.modules.topic.enums.TopicReferenceType.BLOG_REFERENCE.getType());
                    topicReferenceList.add(topicReference);
                }
                // 批量保存
                topicReferenceService.saveBatch(topicReferenceList);
                // 新增话题引用次数
                topicService.increaseReferenceCountBatch(existsTopicIds, 1);
            }
        }
        // 2.处理标签引用
        if (!org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            java.util.List<java.lang.String> existsTagIds = tagService.listByIds(tagIds).stream().map(cn.meteor.beyondclouds.modules.tag.entity.Tag::getTagId).collect(java.util.stream.Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(existsTagIds)) {
                // 如果现在执行的是修改操作，则删除旧话题引用并更新标签的引用
                if (update) {
                    deleteOldTagReferences(blogId);
                }
                java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagList = new java.util.ArrayList<>();
                for (java.lang.String tagId : existsTagIds) {
                    cn.meteor.beyondclouds.modules.blog.entity.BlogTag blogTag = new cn.meteor.beyondclouds.modules.blog.entity.BlogTag();
                    blogTag.setBlogId(blogId);
                    blogTag.setTagId(tagId);
                    blogTagList.add(blogTag);
                }
                // 保存标签引用
                blogTagService.saveBatch(blogTagList);
                // 删除标签引用次数
                tagService.increaseReferenceCountBatch(existsTagIds, 1);
            }
        }
    }

    /**
     * 删除博客博客的旧话题引用
     *
     * @param blogId
     */
    private void deleteOldTopicReferences(java.lang.String blogId) {
        // 删除旧话题引用
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicReferenceQueryWrapper.eq("referencer_id", blogId);
        // 减少对应话题的引用数量
        java.util.List<java.lang.String> referencedTopicIds = topicReferenceService.list(topicReferenceQueryWrapper).stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicReference::getTopicId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(referencedTopicIds)) {
            // 减少话题引用
            topicService.decreaseReferenceCountBatch(referencedTopicIds, 1);
        }
        // 删除话题引用
        topicReferenceService.remove(topicReferenceQueryWrapper);
    }

    /**
     * 删除博客的旧标签引用
     *
     * @param blogId
     */
    private void deleteOldTagReferences(java.lang.String blogId) {
        // 删除旧标签引用
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogTagQueryWrapper.eq("blog_id", blogId);
        // 减少对应话题的引用数量
        java.util.List<java.lang.String> referencedTagIds = blogTagService.list(blogTagQueryWrapper).stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogTag::getTagId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(referencedTagIds)) {
            // 减少标签引用次数
            tagService.decreaseReferenceCountBatch(referencedTagIds, 1);
        }
        // 删除标签引用
        blogTagService.remove(blogTagQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getHotBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        blogQueryWrapper.eq("b.status", 0);
        blogQueryWrapper.eq("b.view_privileges", 1);
        blogQueryWrapper.gt("b.view_number", 19);
        blogQueryWrapper.orderByDesc("b.view_number");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        return blogMapper.selectPageWithTags(page, blogQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getBlogPageByTagId(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String tagId) {
        // 查询blogTagPage
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogTagQueryWrapper.eq("tag_id", tagId);
        blogTagQueryWrapper.orderByDesc("create_time");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagPage = blogTagService.page(page, blogTagQueryWrapper);
        // 根据blogTagPage构造博客分页
        java.util.List<java.lang.String> blogIds = blogTagPage.getRecords().stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogTag::getBlogId).collect(java.util.stream.Collectors.toList());
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        blogPage.setTotal(blogTagPage.getTotal());
        blogPage.setCurrent(blogTagPage.getCurrent());
        blogPage.setPages(blogTagPage.getPages());
        blogPage.setSize(blogTagPage.getSize());
        if (org.springframework.util.CollectionUtils.isEmpty(blogIds)) {
            blogPage.setRecords(java.util.List.of());
        } else {
            // 批量查找博客
            blogPage.setRecords(blogMapper.listByIdsWithTags(blogIds));
        }
        return blogPage;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> getRelatedBlogPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String blogId) {
        // 1.查找博客引用的所有标签id
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        blogTagQueryWrapper.eq("blog_id", blogId);
        java.util.List<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> blogTagList = blogTagService.list(blogTagQueryWrapper);
        java.util.List<java.lang.String> tagIds = blogTagList.stream().map(cn.meteor.beyondclouds.modules.blog.entity.BlogTag::getTagId).collect(java.util.stream.Collectors.toList());
        // 如果博客没有引用标签，则构造一个空的分页返回
        if (org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            return cn.meteor.beyondclouds.util.PageUtils.emptyPage();
        }
        // 如果博客引用了标签，则查询引用的标签下的所有博客ID的分页
        com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> blogIdPage = blogTagService.getRelatedBlogIds(page, tagIds);
        java.util.List<java.lang.String> blogIds = blogIdPage.getRecords();
        if (org.springframework.util.CollectionUtils.isEmpty(blogIds)) {
            return cn.meteor.beyondclouds.util.PageUtils.emptyPage();
        } else {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
            blogPage.setTotal(blogIdPage.getTotal());
            blogPage.setPages(blogIdPage.getPages());
            blogPage.setCurrent(blogIdPage.getCurrent());
            blogPage.setRecords(blogMapper.listByIdsWithTags(blogIds));
            return blogPage;
        }
    }

    @java.lang.Override
    public void updateBlogUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userNick = user.getNickName();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogUserNickUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper();
            blogUserNickUpdateWrapper.eq("user_id", userId);
            blogUserNickUpdateWrapper.set("user_nick", userNick);
            update(blogUserNickUpdateWrapper);
        }
    }

    @java.lang.Override
    public void updateBlogUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userAvatar = user.getUserAvatar();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogUserAvatarUpdeteWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            blogUserAvatarUpdeteWrapper.eq("user_id", userId);
            blogUserAvatarUpdeteWrapper.set("user_avatar", userAvatar);
            update(blogUserAvatarUpdeteWrapper);
        }
    }

    @java.lang.Override
    public long allBlogViewCount(java.lang.String userId) {
        return blogMapper.selectAllViewCount(userId);
    }
}