package cn.meteor.beyondclouds.modules.topic.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 话题引用表，记录了其他模块（项目，博客，问答等）对话题的引用 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class TopicReferenceServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.topic.mapper.TopicReferenceMapper, cn.meteor.beyondclouds.modules.topic.entity.TopicReference> implements cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService {}