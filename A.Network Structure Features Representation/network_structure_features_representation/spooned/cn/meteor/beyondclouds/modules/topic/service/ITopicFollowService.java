package cn.meteor.beyondclouds.modules.topic.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.util.Assert;
/**
 * <p>
 * 话题关注表，记录了用户和话题之间的关注关系 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface ITopicFollowService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.topic.entity.TopicFollow> {
    /**
     * 判断我是否关注过某个话题
     *
     * @param topicId
     * @return  */
    public boolean hasFollowedTopic(java.lang.String topicId);

    /**
     * 获取我关注的所有话题的ID
     *
     * @return  */
    java.util.Set<java.lang.String> getFollowedTopicIds();
}