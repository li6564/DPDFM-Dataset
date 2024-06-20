package cn.meteor.beyondclouds.modules.topic.service;
import cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 话题表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface ITopicService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.topic.entity.Topic> {
    /**
     * 创建话题
     *
     * @param userId
     * @param topicName
     * @return TopicDetail
     */
    cn.meteor.beyondclouds.modules.topic.dto.TopicDTO createTopic(java.lang.String userId, java.lang.String topicName) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;

    /**
     * 检索话题
     *
     * @param keywords
     * @param pageNumber
     * @param pageSize
     * @param updateDegree
     */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> searchTopics(java.lang.String keywords, java.lang.Integer pageNumber, java.lang.Integer pageSize, boolean updateDegree);

    /**
     * 关注话题
     *
     * @param userId
     * @param topicId
     */
    void followTopic(java.lang.String userId, java.lang.String topicId) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;

    /**
     * 话题详情
     *
     * @param identification
     * @param topicAccessWay
     * @return  * @throws TopicServiceException
     */
    cn.meteor.beyondclouds.modules.topic.dto.TopicDTO getTopic(java.lang.String identification, cn.meteor.beyondclouds.modules.topic.enums.TopicAccessWay topicAccessWay) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;

    /**
     * 我关注的话题
     *
     * @param page
     * @param size
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> getTopicsMyFollowed(java.lang.Integer page, java.lang.Integer size, java.lang.String userId);

    /**
     * 话题关注者列表
     *
     * @param page
     * @param size
     * @param topicId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getTopicsFollower(java.lang.Integer page, java.lang.Integer size, java.lang.String topicId) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;

    /**
     * 获取用户的话题分页
     *
     * @param page
     * @param size
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> getTopicPage(java.lang.Integer page, java.lang.Integer size, java.lang.String userId);

    /**
     * 获取话题分页
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> getTopicPage(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 增加话题引用次数
     *
     * @param topicId
     * @param count
     */
    void increaseReferenceCount(java.lang.String topicId, int count);

    /**
     * 批量增加话题引用次数
     *
     * @param topicIds
     * @param count
     */
    void increaseReferenceCountBatch(java.util.Collection<java.lang.String> topicIds, int count);

    /**
     * 减少话题引用次数
     *
     * @param topicId
     * @param count
     */
    void decreaseReferenceCount(java.lang.String topicId, int count);

    /**
     * 批量减少话题引用次数
     *
     * @param topicIds
     * @param count
     */
    void decreaseReferenceCountBatch(java.util.Collection<java.lang.String> topicIds, int count);

    /**
     * 取消关注话题
     *
     * @param userId
     * @param topicId
     * @throws TopicServiceException
     */
    void delFollowTopic(java.lang.String userId, java.lang.String topicId) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;

    /**
     * 热门话题
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> getHotPage(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 该话题下所有的动态
     *
     * @param pageNumber
     * @param pageSize
     * @param topicName
     * @param type
     * @param keywords
     * @throws TopicServiceException
     * @return IPage<Post>
     */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.post.dto.PostDTO> getPostByTopicName(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String topicName, java.lang.Integer type, java.lang.String keywords) throws cn.meteor.beyondclouds.modules.topic.exception.TopicServiceException;

    /**
     * 查找并话题，若话题不存在，则创建话题
     *
     * @param topicNames
     * @param userId
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> queryAndCreateByTopicNames(java.util.List<java.lang.String> topicNames, java.lang.String userId);

    /**
     * 获取热搜话题
     *
     * @return  * @param pageNumber
     * @param pageSize
     */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> getHotSearchTopics(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 获取话题的贡献者分页列表
     *
     * @param topicId
     * @param page
     * @param size
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getTopicContributes(java.lang.String topicId, java.lang.Integer page, java.lang.Integer size);
}