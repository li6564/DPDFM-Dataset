package cn.meteor.beyondclouds.modules.question.service;
import cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 问题回复表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IQuestionReplyService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> {
    /**
     * 回复问题
     *
     * @param questionId
     * 		问题ID
     * @param reply
     * 		回复内容
     * @param userId
     * 		用户ID
     * @throws QuestionReplyServiceException
     * 		问题回复业务异常
     */
    void replyQuestion(java.lang.String questionId, java.lang.String reply, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException;

    /**
     * 采纳回复
     *
     * @param questionId
     * 		问题ID
     * @param replyId
     * 		回复ID
     * @param userId
     * 		用户ID
     * @throws QuestionReplyServiceException
     * 		问题回复业务异常
     */
    void adoptReply(java.lang.String questionId, java.lang.String replyId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException;

    /**
     * 问题的回复列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param questionId
     * 		问题ID
     * @return 页面对象
     * @throws QuestionReplyServiceException
     * 		问题回复业务异常
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> getReplies(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException;

    /**
     * 删除问题回复
     *
     * @param replyId
     * 		回复ID
     * @param userId
     * 		用户ID
     * @throws QuestionReplyServiceException
     * 		问题回复业务异常
     */
    void deleteReply(java.lang.String replyId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException;

    /**
     * 获取用户参加过的问题的ID分页列表
     *
     * @param page
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> participateQuestionIdPage(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.lang.String userId);

    /**
     * 更新用户昵称
     *
     * @param userId
     * 		用户ID
     */
    void updateQuestionReplyUserNick(java.lang.String userId);

    /**
     * 更新用户头像
     *
     * @param userId
     * 		用户ID
     */
    void updateQuestionReplyUserAvatar(java.lang.String userId);

    /**
     * 取消采纳回复
     *
     * @param questionId
     * 		问题ID
     * @param replyId
     * 		回复ID
     * @param userId
     * 		用户ID
     * @throws QuestionReplyServiceException
     * 		问题回复业务异常
     */
    void cancelReplyAdoption(java.lang.String questionId, java.lang.String replyId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException;
}