package cn.meteor.beyondclouds.modules.feedback.service;
import cn.meteor.beyondclouds.modules.feedback.exception.FeedbackServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-14
 */
public interface IFeedbackService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.feedback.entity.Feedback> {
    /**
     * 意见反馈
     *
     * @param feedback
     * @throws FeedbackServiceException
     */
    void feedback(cn.meteor.beyondclouds.modules.feedback.entity.Feedback feedback) throws cn.meteor.beyondclouds.modules.feedback.exception.FeedbackServiceException;

    /**
     * 投诉举报
     *
     * @param complaint
     */
    void complaint(cn.meteor.beyondclouds.modules.feedback.entity.Feedback complaint);
}