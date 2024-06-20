package cn.meteor.beyondclouds.modules.post.service.impl;
import cn.meteor.beyondclouds.modules.post.exception.PostServiceException;
import cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
/**
 * <p>
 * 动态表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class PostServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.post.mapper.PostMapper, cn.meteor.beyondclouds.modules.post.entity.Post> implements cn.meteor.beyondclouds.modules.post.service.IPostService {
    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.post.mapper.PostMapper postMapper;

    private cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService searchDegreeService;

    private cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService;

    private cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setSearchDegreeService(cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService searchDegreeService) {
        this.searchDegreeService = searchDegreeService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostPraiseService(cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService) {
        this.postPraiseService = postPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostCommentService(cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostMapper(cn.meteor.beyondclouds.modules.post.mapper.PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setTopicReferenceService(cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService) {
        this.topicReferenceService = topicReferenceService;
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
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    /**
     * 发布动态
     *
     * @param post
     * @param publishTime
     * @throws ProjectServiceException
     */
    @java.lang.Override
    public void publishPost(cn.meteor.beyondclouds.modules.post.entity.Post post) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        // 1.判断是否视频和图片都传了
        if ((null != post.getPictures()) && (null != post.getVideo())) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.NOT_APPEAR_SAME_TIME);
        }
        // 2.当有图片或者视频时可以没有内容
        if (((null == post.getContent()) && (null == post.getPictures())) && (null == post.getVideo())) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.POST_MUST_NOT_NULL);
        }
        // 2.判断动态类型 0：普通动态 1：图片动态 2：视频动态
        int type;
        if ((null == post.getVideo()) && (null == post.getPictures())) {
            type = 0;
        } else if ((null == post.getVideo()) && (null != post.getPictures())) {
            type = 1;
        } else {
            type = 2;
        }
        post.setType(type);
        // 获取用户头像和昵称
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(post.getUserId());
        post.setUserNick(user.getNickName());
        post.setUserAvatar(user.getUserAvatar());
        java.lang.Integer delay = cn.meteor.beyondclouds.util.TopicUtils.getDelay(post.getContent());
        if ((null != delay) && (delay > 0)) {
            post.setCreateTime(new java.util.Date(java.lang.System.currentTimeMillis() + ((delay * 1000) * 60)));
        }
        post.setContent(cn.meteor.beyondclouds.util.TopicUtils.encodeTopic(post.getContent()));
        // 2.保存动态
        save(post);
        // 处理话题引用
        java.lang.String postContent = post.getContent();
        if (!org.springframework.util.StringUtils.isEmpty(postContent)) {
            // 解析话题，若话题不存在则创建话题
            java.util.List<java.lang.String> topicNames = cn.meteor.beyondclouds.util.TopicUtils.parseTopics(post.getContent());
            if (!org.springframework.util.CollectionUtils.isEmpty(topicNames)) {
                java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topics = topicService.queryAndCreateByTopicNames(topicNames, post.getUserId());
                java.util.List<java.lang.String> unLockedTopicNames = topics.stream().map(cn.meteor.beyondclouds.modules.topic.entity.Topic::getTopicName).collect(java.util.stream.Collectors.toList());
                // 筛选出被锁定的话题
                java.util.List<java.lang.String> lockedTopics = topicNames.stream().filter(topicName -> !unLockedTopicNames.contains(topicName)).collect(java.util.stream.Collectors.toList());
                // 从动态中删除被锁定的话题
                if (!org.springframework.util.CollectionUtils.isEmpty(lockedTopics)) {
                    post.setContent(cn.meteor.beyondclouds.util.TopicUtils.clearLockedTopics(postContent, lockedTopics));
                    updateById(post);
                }
                if (!org.springframework.util.CollectionUtils.isEmpty(unLockedTopicNames)) {
                    // 提升话题热搜毒
                    java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreeList = new java.util.ArrayList<>();
                    topics.forEach(topic -> {
                        double score = cn.meteor.beyondclouds.modules.search.util.TopicScoreUtils.calculateScore(topic.getTopicName(), topic.getTopicName());
                        if (score > 0) {
                            cn.meteor.beyondclouds.modules.search.entity.SearchDegree searchDegree = new cn.meteor.beyondclouds.modules.search.entity.SearchDegree();
                            searchDegree.setItemId(topic.getTopicId());
                            searchDegree.setItemType(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC.name());
                            searchDegree.setDegree(score);
                            searchDegreeList.add(searchDegree);
                        }
                    });
                    if (!org.springframework.util.CollectionUtils.isEmpty(searchDegreeList)) {
                        searchDegreeService.updateTopicDegreeBatch(searchDegreeList);
                    }
                    java.util.List<java.lang.String> topicIds = topics.stream().map(cn.meteor.beyondclouds.modules.topic.entity.Topic::getTopicId).collect(java.util.stream.Collectors.toList());
                    // 关联话题和动态
                    java.util.List<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferences = new java.util.ArrayList<>();
                    for (java.lang.String topicId : topicIds) {
                        cn.meteor.beyondclouds.modules.topic.entity.TopicReference topicReference = new cn.meteor.beyondclouds.modules.topic.entity.TopicReference();
                        topicReference.setUserId(user.getUserId());
                        topicReference.setTopicId(topicId);
                        topicReference.setReferencerId(post.getPostId());
                        topicReference.setReferencerType(cn.meteor.beyondclouds.modules.topic.enums.TopicReferenceType.POST_REFERENCE.getType());
                        topicReferences.add(topicReference);
                    }
                    // 批量保存
                    topicReferenceService.saveBatch(topicReferences);
                    // 新增话题引用次数
                    topicService.increaseReferenceCountBatch(topicIds, 1);
                }
            }
        }
        // 发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST, post.getPostId()));
    }

    /**
     * 删除动态
     *
     * @param postId
     * @param userId
     * @throws PostServiceException
     */
    @java.lang.Override
    public void deletePost(java.lang.String postId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.post.exception.PostServiceException {
        // 1.判断是不是该用户发布的动态
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("post_id", postId);
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("status", 0);
        cn.meteor.beyondclouds.modules.post.entity.Post post = getOne(queryWrapper);
        if (null == post) {
            throw new cn.meteor.beyondclouds.modules.post.exception.PostServiceException(cn.meteor.beyondclouds.modules.post.enums.PostErrorCode.USER_POST_NOT_FOUND);
        }
        // 2.删除动态里的话题引用
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicReferenceQueryWrapper.eq("referencer_id", postId);
        // 所有的话题引用减1
        java.util.List<java.lang.String> existsTopicIds = topicReferenceService.list(topicReferenceQueryWrapper).stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicReference::getTopicId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(existsTopicIds)) {
            topicService.decreaseReferenceCountBatch(existsTopicIds, 1);
            topicReferenceService.remove(topicReferenceQueryWrapper);
        }
        // 3.删除动态评论
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostComment> postCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        postCommentQueryWrapper.eq("post_id", postId);
        postCommentService.remove(postCommentQueryWrapper);
        // 4.删除动态点赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        postPraiseQueryWrapper.eq("target_id", postId);
        postPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
        postPraiseService.remove(postPraiseQueryWrapper);
        // 5.删除动态
        removeById(postId);
        // 6.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.deleteMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST, post.getPostId()));
    }

    /**
     * 获取动态列表
     *
     * @param pageNumber
     * @param pageSize
     * @param type
     * @return  */
    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getPostPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer type) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.Post> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("status", 0);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.le("create_time", new java.util.Date());
        if (null != type) {
            queryWrapper.eq("type", type);
        }
        return toPageDTO(page(postPage, queryWrapper));
    }

    /**
     * 个人动态列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getUserPostPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("user_id", userId);
        if ((!cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) || (!cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId().equals(userId))) {
            queryWrapper.eq("status", 0);
        }
        queryWrapper.orderByDesc("create_time");
        if ((!cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) || (!cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId().equals(userId))) {
            queryWrapper.le("create_time", new java.util.Date());
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        return toPageDTO(page(postPage, queryWrapper));
    }

    /**
     * 更新动态里的用户头像
     *
     * @param userId
     */
    @java.lang.Override
    public void updatePostUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.post.entity.Post> postUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            postUpdateWrapper.eq("user_id", userId);
            postUpdateWrapper.set("user_avatar", user.getUserAvatar());
            update(postUpdateWrapper);
        }
    }

    /**
     * 更新动态里的用户昵称
     *
     * @param userId
     */
    @java.lang.Override
    public void updatePostUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.post.entity.Post> postUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            postUpdateWrapper.eq("user_id", userId);
            postUpdateWrapper.set("user_nick", user.getNickName());
            update(postUpdateWrapper);
        }
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getFollowedPostPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 查询出我所关注的用户
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> userFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userFollowQueryWrapper.eq("follower_id", userId);
        userFollowQueryWrapper.eq("follow_status", 0);
        java.util.List<java.lang.String> followedIds = userFollowService.list(userFollowQueryWrapper).stream().map(cn.meteor.beyondclouds.modules.user.entity.UserFollow::getFollowedId).collect(java.util.stream.Collectors.toList());
        // 获取我关注的用户的动态
        if (!org.springframework.util.CollectionUtils.isEmpty(followedIds)) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.Post> postQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            postQueryWrapper.in("user_id", followedIds);
            postQueryWrapper.eq("status", 0);
            postQueryWrapper.orderByDesc("create_time");
            postQueryWrapper.le("create_time", new java.util.Date());
            return toPageDTO(page(postPage, postQueryWrapper));
        } else {
            return cn.meteor.beyondclouds.util.PageUtils.emptyPageDTO();
        }
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getRecommendPosts(java.lang.Integer page, java.lang.Integer size) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage = postMapper.selectRecommendPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, size));
        return toPageDTO(postPage);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getRecommendPostsInTopic(java.lang.String topicId, java.lang.Integer page, java.lang.Integer size) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage = postMapper.selectRecommendPageInTopic(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.post.entity.Post>(page, size), topicId);
        return toPageDTO(postPage);
    }

    private cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> toPageDTO(com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage) {
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(postPage, pageDTO);
        if (postPage.getRecords().size() == 0) {
            pageDTO.setDataList(java.util.List.of());
            return pageDTO;
        }
        java.util.List<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postDTOList;
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.util.List<java.lang.String> postIds = postPage.getRecords().stream().map(cn.meteor.beyondclouds.modules.post.entity.Post::getPostId).collect(java.util.stream.Collectors.toList());
            // 获取点赞情况
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            postPraiseQueryWrapper.in("target_id", postIds);
            postPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
            postPraiseQueryWrapper.eq("user_id", cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId());
            java.util.List<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseList = postPraiseService.list(postPraiseQueryWrapper);
            java.util.Set<java.lang.String> praisedPostIds = postPraiseList.stream().map(cn.meteor.beyondclouds.modules.post.entity.PostPraise::getTargetId).collect(java.util.stream.Collectors.toSet());
            postDTOList = postPage.getRecords().stream().map(post -> {
                cn.meteor.beyondclouds.modules.post.dto.PostDTO postDTO = new cn.meteor.beyondclouds.modules.post.dto.PostDTO();
                org.springframework.beans.BeanUtils.copyProperties(post, postDTO);
                if (!org.springframework.util.StringUtils.isEmpty(post.getPictures())) {
                    postDTO.setPictures(post.getPictures().split(","));
                }
                postDTO.setPraised(praisedPostIds.contains(post.getPostId()));
                postDTO.setFollowedAuthor(userFollowService.hasFollowedUser(post.getUserId()));
                return postDTO;
            }).collect(java.util.stream.Collectors.toList());
        } else {
            postDTOList = postPage.getRecords().stream().map(post -> {
                cn.meteor.beyondclouds.modules.post.dto.PostDTO postDTO = new cn.meteor.beyondclouds.modules.post.dto.PostDTO();
                org.springframework.beans.BeanUtils.copyProperties(post, postDTO);
                if (!org.springframework.util.StringUtils.isEmpty(post.getPictures())) {
                    postDTO.setPictures(post.getPictures().split(","));
                }
                postDTO.setFollowedAuthor(false);
                postDTO.setPraised(false);
                return postDTO;
            }).collect(java.util.stream.Collectors.toList());
        }
        pageDTO.setDataList(postDTOList);
        return pageDTO;
    }
}