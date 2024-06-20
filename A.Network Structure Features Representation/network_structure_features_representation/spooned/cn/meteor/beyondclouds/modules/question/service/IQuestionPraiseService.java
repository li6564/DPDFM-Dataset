package cn.meteor.beyondclouds.modules.question.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
public interface IQuestionPraiseService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> {
    /**
     * 问题点赞
     *
     * @param currentUserId
     * @param questionId
     */
    void questionPraise(java.lang.String currentUserId, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 取消问题点赞
     *
     * @param currentUserId
     * @param questionId
     */
    void deleteQuestionPraise(java.lang.String currentUserId, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 问题回复点赞
     *
     * @param currentUserId
     * @param replyId
     */
    void questionReplyPraise(java.lang.String currentUserId, java.lang.String replyId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 取消问题回复点赞
     *
     * @param currentUserId
     * @param replyId
     */
    void deleteQuestionReplyPraise(java.lang.String currentUserId, java.lang.String replyId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 问题点赞列表
     *
     * @param page
     * @param size
     * @param questionId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getQuestionPraises(java.lang.Integer page, java.lang.Integer size, java.lang.String questionId);
}