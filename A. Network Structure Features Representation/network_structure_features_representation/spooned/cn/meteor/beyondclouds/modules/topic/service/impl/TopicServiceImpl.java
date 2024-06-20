package cn.meteor.beyondclouds.modules.topic.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.statement.select.Top;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
/**
 * <p>
 * 话题表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class TopicServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.topic.mapper.TopicMapper, cn.meteor.beyondclouds.modules.topic.entity.Topic> implements cn.meteor.beyondclouds.modules.topic.service.ITopicService {
    private cn.meteor.beyondclouds.modules.topic.service.ITopicFollowService topicFollowService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.topic.mapper.TopicMapper topicMapper;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService;

    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService searchDegreeService;

    private cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public TopicServiceImpl(cn.meteor.beyondclouds.modules.topic.service.ITopicFollowService topicFollowService, cn.meteor.beyondclouds.modules.topic.mapper.TopicMapper topicMapper, cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService) {
        this.topicFollowService = topicFollowService;
        this.topicMapper = topicMapper;
        this.topicReferenceService = topicReferenceService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostPraiseService(cn.meteor.beyondclouds.modules.post.service.IPostPraiseService postPraiseService) {
        this.postPraiseService = postPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setSearchDegreeService(cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService searchDegreeService) {
        this.searchDegreeService = searchDegreeService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserStatisticsService(cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostService(cn.meteor.beyondclouds.modules.post.service.IPostService postService) {
        this.postService = postService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.topic.dto.TopicDTO createTopic(java.lang.String userId, java.lang.String topicName) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException {
        // 检测话题名称是否合法
        topicName = topicName.replace("#", "").trim();
        if (org.apache.commons.lang3.StringUtils.isBlank(topicName)) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_FORMAT_ERROR);
        }
        // 1. 检测是否存在该话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("binary topic_name", topicName);
        if (getOne(topicQueryWrapper) != null) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_EXISTS);
        }
        // 2. 创建话题
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic = new cn.meteor.beyondclouds.modules.topic.entity.Topic();
        topic.setUserId(userId);
        topic.setTopicName(topicName);
        save(topic);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC, topic.getTopicId()));
        // 3. 返回话题的id与话题名称
        cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = new cn.meteor.beyondclouds.modules.topic.dto.TopicDTO();
        org.springframework.beans.BeanUtils.copyProperties(topic, topicDTO);
        return topicDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> searchTopics(java.lang.String keywords, java.lang.Integer pageNumber, java.lang.Integer pageSize, boolean updateDegree) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.like("topic_name", keywords);
        topicQueryWrapper.eq("status", 0);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicPage = page(page, topicQueryWrapper);
        // 更新话题的搜索量
        if (updateDegree) {
            java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicList = topicPage.getRecords();
            if (topicList.size() > 0) {
                java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreeList = new java.util.ArrayList<>();
                topicList.forEach(topic -> {
                    double score = cn.meteor.beyondclouds.modules.search.util.TopicScoreUtils.calculateScore(topic.getTopicName(), keywords);
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
            }
        }
        return toPageDTO(topicPage);
    }

    /**
     * 将IPage<Topic>转换成PageDTO<TopicDTO>
     *
     * @param topicPage
     * @return  */
    private cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> toPageDTO(com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicPage) {
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicList = topicPage.getRecords();
        java.util.List<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicDTOList = new java.util.ArrayList<>();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.util.Set<java.lang.String> followedTopicIds = topicFollowService.getFollowedTopicIds();
            topicList.forEach(topic -> {
                cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = new cn.meteor.beyondclouds.modules.topic.dto.TopicDTO();
                org.springframework.beans.BeanUtils.copyProperties(topic, topicDTO);
                topicDTO.setFollowedTopic(followedTopicIds.contains(topic.getTopicId()));
                topicDTOList.add(topicDTO);
            });
        } else {
            topicList.forEach(topic -> {
                cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = new cn.meteor.beyondclouds.modules.topic.dto.TopicDTO();
                org.springframework.beans.BeanUtils.copyProperties(topic, topicDTO);
                topicDTO.setFollowedTopic(false);
                topicDTOList.add(topicDTO);
            });
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(topicPage, pageDTO);
        pageDTO.setDataList(topicDTOList);
        return pageDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.topic.dto.TopicDTO getTopic(java.lang.String identification, cn.meteor.beyondclouds.modules.topic.enums.TopicAccessWay topicAccessWay) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException {
        // 1. 判断by为话题id，还是话题名称
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic;
        // 2. 获取话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq(topicAccessWay.getFieldName(), identification);
        topicQueryWrapper.eq("status", 0);
        topic = getOne(topicQueryWrapper);
        // 找不到该话题，抛出业务异常
        if (topic == null) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_NOT_EXISTS);
        }
        cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = toTopicDTO(topic);
        // 获取话题创建者的信息
        cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics = userStatisticsService.getById(topic.getUserId());
        topicDTO.setUserStatistics(userStatistics);
        return topicDTO;
    }

    /**
     * 将topic转换为topicDTO
     *
     * @param topic
     * @return  */
    private cn.meteor.beyondclouds.modules.topic.dto.TopicDTO toTopicDTO(cn.meteor.beyondclouds.modules.topic.entity.Topic topic) {
        cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = new cn.meteor.beyondclouds.modules.topic.dto.TopicDTO();
        org.springframework.beans.BeanUtils.copyProperties(topic, topicDTO);
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            topicDTO.setFollowedTopic(topicFollowService.hasFollowedTopic(topic.getTopicId()));
        } else {
            topicDTO.setFollowedTopic(false);
        }
        return topicDTO;
    }

    @java.lang.Override
    public void followTopic(java.lang.String userId, java.lang.String topicId) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException {
        // 1. 获取话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("topic_id", topicId);
        topicQueryWrapper.eq("status", 0);
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic = getOne(topicQueryWrapper);
        // 2. 找不到该话题，抛出业务异常
        if (topic == null) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_NOT_EXISTS);
        }
        // 3. 判断用户是否已经关注该话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicFollowQueryWrapper.eq("user_id", userId);
        topicFollowQueryWrapper.eq("topic_id", topicId);
        if (null != topicFollowService.getOne(topicFollowQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.AlREADY_FOLLOWED);
        }
        // 4. 关注话题
        cn.meteor.beyondclouds.modules.topic.entity.TopicFollow topicFollow = new cn.meteor.beyondclouds.modules.topic.entity.TopicFollow();
        topicFollow.setTopicId(topicId);
        topicFollow.setUserId(userId);
        topicFollowService.save(topicFollow);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> getTopicsMyFollowed(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        // 1. 通过用户id获取关注的所有话题id
        // 设置分页信息
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 2.查询TopicFollow
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicFollowQueryWrapper.eq("user_id", userId);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowPage = topicFollowService.page(page, topicFollowQueryWrapper);
        // 获取查询到的topicId
        java.util.List<java.lang.String> topicIds = topicFollowPage.getRecords().stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicFollow::getTopicId).collect(java.util.stream.Collectors.toList());
        // 3. 通过话题id批量查询话题
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topics;
        if (!org.springframework.util.CollectionUtils.isEmpty(topicIds)) {
            topics = listByIds(topicIds);
        } else {
            topics = java.util.List.of();
        }
        // 5.构造分页结果
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> userPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        userPage.setSize(topicFollowPage.getSize());
        userPage.setCurrent(topicFollowPage.getCurrent());
        userPage.setPages(topicFollowPage.getPages());
        userPage.setTotal(topicFollowPage.getTotal());
        userPage.setRecords(topics);
        return userPage;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getTopicsFollower(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String topicId) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("status", 0);
        topicQueryWrapper.eq("topic_id", topicId);
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic = getOne(topicQueryWrapper);
        if (null == topic) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_NOT_EXISTS);
        }
        // 1. 通过话题id获取该话题所有关注着
        // 设置分页信息
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 2.查询TopicFollow
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicFollowQueryWrapper.eq("topic_id", topicId);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowPage = topicFollowService.page(page, topicFollowQueryWrapper);
        // 获取查询到的userId
        java.util.List<java.lang.String> followerIds = topicFollowPage.getRecords().stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicFollow::getUserId).collect(java.util.stream.Collectors.toList());
        // 3. 通过用户id批量查询用户
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.User> followers;
        if (!org.springframework.util.CollectionUtils.isEmpty(followerIds)) {
            followers = userService.listByIds(followerIds);
        } else {
            followers = java.util.List.of();
        }
        // 查询所有用户的统计信息
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsList = userStatisticsService.listByIds(followerIds);
        // 将统计信息列表转换成HashMap
        java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsMap = userStatisticsList.stream().collect(java.util.stream.Collectors.toMap(cn.meteor.beyondclouds.modules.user.entity.UserStatistics::getUserId, userStatistics -> userStatistics));
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOList = new java.util.ArrayList<>();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.util.Set<java.lang.String> followedUserIds = userFollowService.getCurrentUserFollowedUserIds();
            followers.forEach(user -> {
                cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO userFollowDTO = new cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO();
                org.springframework.beans.BeanUtils.copyProperties(user, userFollowDTO);
                userFollowDTO.setFollowedUser(followedUserIds.contains(user.getUserId()));
                userFollowDTO.setUserNick(user.getNickName());
                userFollowDTO.setStatistics(userStatisticsMap.get(user.getUserId()));
                userFollowDTOList.add(userFollowDTO);
            });
        } else {
            followers.forEach(user -> {
                cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO userFollowDTO = new cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO();
                org.springframework.beans.BeanUtils.copyProperties(user, userFollowDTO);
                userFollowDTO.setUserNick(user.getNickName());
                userFollowDTO.setFollowedUser(false);
                userFollowDTO.setStatistics(userStatisticsMap.get(user.getUserId()));
                userFollowDTOList.add(userFollowDTO);
            });
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(topicFollowPage, userFollowDTOPage);
        userFollowDTOPage.setDataList(userFollowDTOList);
        return userFollowDTOPage;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> getTopicPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("user_id", userId);
        topicQueryWrapper.orderByDesc("create_time");
        return page(page, topicQueryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> getTopicPage(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("status", 0);
        topicQueryWrapper.orderByDesc("create_time");
        return toPageDTO(page(page, topicQueryWrapper));
    }

    @java.lang.Override
    public void increaseReferenceCount(java.lang.String topicId, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        topicUpdateWrapper.eq("topic_id", topicId);
        topicUpdateWrapper.setSql("reference_count=reference_count + " + count);
        update(topicUpdateWrapper);
    }

    @java.lang.Override
    public void increaseReferenceCountBatch(java.util.Collection<java.lang.String> topicIds, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        topicUpdateWrapper.in("topic_id", topicIds);
        topicUpdateWrapper.setSql("reference_count=reference_count + " + count);
        update(topicUpdateWrapper);
    }

    @java.lang.Override
    public void decreaseReferenceCount(java.lang.String topicId, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        topicUpdateWrapper.eq("topic_id", topicId);
        topicUpdateWrapper.setSql("reference_count=reference_count - " + count);
        update(topicUpdateWrapper);
    }

    @java.lang.Override
    public void decreaseReferenceCountBatch(java.util.Collection<java.lang.String> topicIds, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        topicUpdateWrapper.in("topic_id", topicIds);
        topicUpdateWrapper.setSql("reference_count=reference_count - " + count);
        update(topicUpdateWrapper);
    }

    @java.lang.Override
    public void delFollowTopic(java.lang.String userId, java.lang.String topicId) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException {
        // 1. 获取话题
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic = getById(topicId);
        // 2. 找不到该话题，抛出业务异常
        if (topic == null) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_NOT_EXISTS);
        }
        // 3. 判断用户是否关注该话题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> delTopicFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        delTopicFollowQueryWrapper.eq("user_id", userId);
        delTopicFollowQueryWrapper.eq("topic_id", topicId);
        if (null == topicFollowService.getOne(delTopicFollowQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.NOT_FOLLOWED);
        }
        // 4. 取消关注话题
        topicFollowService.remove(delTopicFollowQueryWrapper);
    }

    /**
     * 热门话题
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> getHotPage(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("status", 0);
        topicQueryWrapper.orderByDesc("reference_count");
        return toPageDTO(page(page, topicQueryWrapper));
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getPostByTopicName(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String topicName, java.lang.Integer type, java.lang.String keywords) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException {
        // 1. 设置分页信息
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 2. 判断该话题是否存在
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.eq("binary topic_name", topicName);
        topicQueryWrapper.eq("status", 0);
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic = getOne(topicQueryWrapper);
        if (topic == null) {
            throw new cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException(cn.meteor.beyondclouds.modules.topic.enums.TopicErrorCode.TOPIC_NOT_EXISTS);
        }
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("tr.topic_id", topic.getTopicId());
        queryWrapper.eq("tr.referencer_type", 3);
        if (null != type) {
            queryWrapper.eq("p.type", type);
        }
        if (null != keywords) {
            queryWrapper.like("p.content", keywords);
        }
        queryWrapper.orderByDesc("p.create_time");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> postPage = topicMapper.searchPostInTopicWithConditions(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.post.entity.Post>(pageNumber, pageSize), queryWrapper);
        java.util.List<cn.meteor.beyondclouds.modules.post.entity.Post> posts = postPage.getRecords();
        java.util.List<java.lang.String> postIds = posts.stream().map(cn.meteor.beyondclouds.modules.post.entity.Post::getPostId).collect(java.util.stream.Collectors.toList());
        java.util.List<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postDTOList = new java.util.ArrayList<>();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            // 获取点赞情况
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            postPraiseQueryWrapper.in("target_id", postIds);
            postPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.post.enums.PostPraiseType.POST_PRAISE.getPraiseType());
            postPraiseQueryWrapper.eq("user_id", cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId());
            java.util.List<cn.meteor.beyondclouds.modules.post.entity.PostPraise> postPraiseList = postPraiseService.list(postPraiseQueryWrapper);
            java.util.Set<java.lang.String> praisedPostIds = postPraiseList.stream().map(cn.meteor.beyondclouds.modules.post.entity.PostPraise::getTargetId).collect(java.util.stream.Collectors.toSet());
            java.util.Set<java.lang.String> followedUserIds = userFollowService.getCurrentUserFollowedUserIds();
            posts.forEach(post -> {
                cn.meteor.beyondclouds.modules.post.dto.PostDTO postDTO = new cn.meteor.beyondclouds.modules.post.dto.PostDTO();
                org.springframework.beans.BeanUtils.copyProperties(post, postDTO);
                postDTO.setFollowedAuthor(followedUserIds.contains(post.getUserId()));
                postDTO.setPraised(praisedPostIds.contains(post.getPostId()));
                postDTOList.add(postDTO);
            });
        } else {
            posts.forEach(post -> {
                cn.meteor.beyondclouds.modules.post.dto.PostDTO postDTO = new cn.meteor.beyondclouds.modules.post.dto.PostDTO();
                org.springframework.beans.BeanUtils.copyProperties(post, postDTO);
                postDTO.setFollowedAuthor(false);
                postDTO.setPraised(false);
                postDTOList.add(postDTO);
            });
        }
        // 6.构造分页结果
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> postDTOPage = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(postPage, postDTOPage);
        postDTOPage.setDataList(postDTOList);
        return postDTOPage;
    }

    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> queryAndCreateByTopicNames(java.util.List<java.lang.String> topicNames, java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicQueryWrapper.in("binary topic_name", topicNames);
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topics = list(topicQueryWrapper);
        // 查询不存在的话题
        java.util.List<java.lang.String> topicNameExists = topics.stream().map(cn.meteor.beyondclouds.modules.topic.entity.Topic::getTopicName).collect(java.util.stream.Collectors.toList());
        java.util.List<java.lang.String> topicNameNotExists = topicNames.stream().filter(topicName -> !topicNameExists.contains(topicName)).collect(java.util.stream.Collectors.toList());
        // 创建不存在的话题并添加到topics
        topicNameNotExists.forEach(topicName -> {
            cn.meteor.beyondclouds.modules.topic.entity.Topic topic = new cn.meteor.beyondclouds.modules.topic.entity.Topic();
            topic.setStatus(0);
            topic.setTopicName(topicName);
            topic.setUserId(userId);
            save(topic);
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC, topic.getTopicId()));
            topics.add(topic);
        });
        // 过滤掉被锁定的话题
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicList = topics.stream().filter(topic -> topic.getStatus() == 0).collect(java.util.stream.Collectors.toList());
        return topicList;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> getHotSearchTopics(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicPage = topicMapper.selectHotSearchPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize));
        java.util.List<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> topicList = topicPage.getRecords();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.util.Set<java.lang.String> followedTopicIds = topicFollowService.getFollowedTopicIds();
            topicList.forEach(topic -> {
                cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = new cn.meteor.beyondclouds.modules.topic.dto.TopicDTO();
                org.springframework.beans.BeanUtils.copyProperties(topic, topicDTO);
                topicDTO.setFollowedTopic(followedTopicIds.contains(topic.getTopicId()));
            });
        } else {
            topicList.forEach(topic -> {
                cn.meteor.beyondclouds.modules.topic.dto.TopicDTO topicDTO = new cn.meteor.beyondclouds.modules.topic.dto.TopicDTO();
                org.springframework.beans.BeanUtils.copyProperties(topic, topicDTO);
                topicDTO.setFollowedTopic(false);
            });
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(topicPage, pageDTO);
        pageDTO.setDataList(topicList);
        return pageDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getTopicContributes(java.lang.String topicId, java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> userIdPage = topicMapper.selectTopicContributeIdPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize), topicId);
        java.util.List<java.lang.String> userIds = userIdPage.getRecords();
        int index = 0;
        java.util.Map<java.lang.String, java.lang.Integer> userIdSortMap = new java.util.HashMap<>(userIds.size());
        for (int i = 0; i < userIds.size(); i++) {
            userIdSortMap.put(userIds.get(i), i);
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userIdPage, userFollowDTOPage);
        if (!org.springframework.util.CollectionUtils.isEmpty(userIds)) {
            java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userInfoDTOList = userService.listByIdsWithStatistics(userIds);
            if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
                java.util.Set<java.lang.String> followedUserIds = userFollowService.getCurrentUserFollowedUserIds();
                userInfoDTOList.forEach(userFollowDTO -> {
                    userFollowDTO.setFollowedUser(followedUserIds.contains(userFollowDTO.getUserId()));
                });
            } else {
                userInfoDTOList.forEach(userFollowDTO -> {
                    userFollowDTO.setFollowedUser(false);
                });
            }
            // 对列表进行重新排序
            java.util.Collections.sort(userInfoDTOList, new java.util.Comparator<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO>() {
                @java.lang.Override
                public int compare(cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO o1, cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO o2) {
                    return userIdSortMap.get(o1.getUserId()) - userIdSortMap.get(o2.getUserId());
                }
            });
            userFollowDTOPage.setDataList(userInfoDTOList);
        } else {
            userFollowDTOPage.setDataList(java.util.List.of());
        }
        return userFollowDTOPage;
    }
}