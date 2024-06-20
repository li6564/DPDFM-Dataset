package cn.meteor.beyondclouds.modules.question.service;
import cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
/**
 * <p>
 * 问题回复评论表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IQuestionReplyCommentService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> {
    /**
     * 发表评论
     *
     * @param userId
     * 		用户ID
     * @param replyId
     * 		回复ID
     * @param parentId
     * 		父评论ID
     * @param comment
     * 		评论内容
     * @throws QuestionReplyCommentServiceException
     * 		问题回复评论业务异常
     */
    void publishReplyComment(java.lang.String userId, java.lang.String replyId, java.lang.String parentId, java.lang.String comment) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException;

    /**
     * 删除评论
     *
     * @param userId
     * 		用户ID
     * @param commentId
     * 		评论ID
     * @throws QuestionReplyCommentServiceException
     * 		问题回复评论业务异常
     */
    void deleteReplyComment(java.lang.String userId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException;

    /**
     * 评论列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param replyId
     * 		回复ID
     * @param parentId
     * 		父评论ID
     * @return 分页对象
     * @throws QuestionReplyCommentServiceException
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> getReplyCommentPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String replyId, java.lang.String parentId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException;

    /**
     * 更新用户昵称
     *
     * @param userId
     * 		用户ID
     */
    void updateQuestionReplyCommentUserNick(java.lang.String userId);

    /**
     * 更新用户头像
     *
     * @param userId
     * 		用户ID
     */
    void updateQuestionReplyCommentUserAvatar(java.lang.String userId);
}