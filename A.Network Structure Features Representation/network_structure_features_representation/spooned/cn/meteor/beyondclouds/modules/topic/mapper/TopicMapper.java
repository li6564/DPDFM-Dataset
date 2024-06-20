package cn.meteor.beyondclouds.modules.topic.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * <p>
 * 话题表 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Component
public interface TopicMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.topic.entity.Topic> {
    /**
     * 检索话题
     *
     * @param keywords
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> searchTopics(java.lang.String keywords);

    /**
     * 热搜话题
     *
     * @param topicPage
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.dto.TopicDTO> selectHotSearchPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicPage);

    /**
     * 搜索话题下的所有动态
     *
     * @param postPage
     * @param queryWrapper
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> searchPostInTopicWithConditions(com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.post.entity.Post> postPage, @org.apache.ibatis.annotations.Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
    com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<?> queryWrapper);

    /**
     * 获取话题贡献者的ID
     *
     * @param page
     * @param topicId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> selectTopicContributeIdPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<java.lang.String> page, @org.apache.ibatis.annotations.Param("topicId")
    java.lang.String topicId);
}