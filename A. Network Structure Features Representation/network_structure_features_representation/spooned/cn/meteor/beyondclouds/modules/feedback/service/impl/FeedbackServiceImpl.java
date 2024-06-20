package cn.meteor.beyondclouds.modules.feedback.service.impl;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-14
 */
@org.springframework.stereotype.Service
public class FeedbackServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.feedback.mapper.FeedbackMapper, cn.meteor.beyondclouds.modules.feedback.entity.Feedback> implements cn.meteor.beyondclouds.modules.feedback.service.IFeedbackService {
    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public FeedbackServiceImpl(cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @java.lang.Override
    public void feedback(cn.meteor.beyondclouds.modules.feedback.entity.Feedback feedback) throws cn.meteor.beyondclouds.modules.feedback.exception.FeedbackServiceException {
        // 2.设置反馈类型，1-反馈，2-举报
        feedback.setFeedbackType(cn.meteor.beyondclouds.modules.feedback.enums.FeedbackType.FEEDBACK.getType());
        // 3.保存意见反馈信息
        save(feedback);
    }

    @java.lang.Override
    public void complaint(cn.meteor.beyondclouds.modules.feedback.entity.Feedback complaint) {
        // 1.设置反馈类型，1-反馈，2-举报
        complaint.setFeedbackType(cn.meteor.beyondclouds.modules.feedback.enums.FeedbackType.COMPLAINT.getType());
        // 2.保存投诉举报信息
        save(complaint);
    }
}