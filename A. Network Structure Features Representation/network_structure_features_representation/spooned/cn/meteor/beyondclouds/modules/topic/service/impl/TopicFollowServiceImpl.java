package cn.meteor.beyondclouds.modules.topic.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
/**
 * <p>
 * 话题关注表，记录了用户和话题之间的关注关系 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class TopicFollowServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.topic.mapper.TopicFollowMapper, cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> implements cn.meteor.beyondclouds.modules.topic.service.ITopicFollowService {
    @java.lang.Override
    public boolean hasFollowedTopic(java.lang.String topicId) {
        org.springframework.util.Assert.isTrue(cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated(), "user must authenticated");
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicFollowQueryWrapper.eq("user_id", currentUserId);
        topicFollowQueryWrapper.eq("topic_id", topicId);
        return count(topicFollowQueryWrapper) > 0;
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> getFollowedTopicIds() {
        org.springframework.util.Assert.isTrue(cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated(), "user must authenticated");
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollowQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicFollowQueryWrapper.eq("user_id", currentUserId);
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> topicFollows = list(topicFollowQueryWrapper);
        return topicFollows.stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicFollow::getTopicId).collect(java.util.stream.Collectors.toSet());
    }
}