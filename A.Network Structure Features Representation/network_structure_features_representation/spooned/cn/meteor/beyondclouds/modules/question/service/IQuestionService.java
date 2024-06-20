package cn.meteor.beyondclouds.modules.question.service;
import cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;
import cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 问题表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IQuestionService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.question.entity.Question> {
    /**
     * 发布问题
     *
     * @param question
     * 		问题基本信息
     * @param content
     * 		问题详细信息
     * @param contentHtml
     * @param tags
     * 		问题标签
     * @param topicIds
     * 		话题ID
     * @throws QuestionServiceException
     * 		问题业务异常
     */
    void postQuestion(cn.meteor.beyondclouds.modules.question.entity.Question question, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> tags, java.util.List<java.lang.String> topicIds) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 删除问题
     *
     * @param questionId
     * 		问题ID
     * @param userId
     * 		用户ID
     * @throws QuestionServiceException
     * 		问题业务异常
     */
    void deleteQuestion(java.lang.String questionId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 修改问题
     *
     * @param question
     * 		问题基本信息
     * @param content
     * @param contentHtml
     * 		问题详细信息
     * @param tags
     * 		问题标签
     * @param topicIds
     * 		话题ID
     * @throws QuestionServiceException
     * 		问题业务异常
     */
    void updateQuestion(cn.meteor.beyondclouds.modules.question.entity.Question question, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> tags, java.util.List<java.lang.String> topicIds) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 问题详情
     *
     * @param questionId
     * 		问题ID
     * @param subject
     * @param updateViewNum
     * @return 问题详情
     * @throws QuestionServiceException
     * 		问题业务异常
     * @throws QuestionTagServiceException
     * 		问题标签业务异常
     */
    cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO getQuestionDetail(java.lang.String questionId, cn.meteor.beyondclouds.core.authentication.Subject subject, boolean updateViewNum) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException;

    /**
     * 获取问答列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param categoryId
     * @return 分页对象
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getQuestionPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer categoryId);

    /**
     * 获取用户的问答列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param userId
     * 		用户ID
     * @return 分页对象
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getUserQuestionPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 获取用户参与的问答列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param userId
     * 		用户ID
     * @return 分页对象
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getUserParticipatePage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 获取热门回答列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @return 分页对象
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getHotsQuestionsPage(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 相关问答
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param questionId
     * 		问题ID
     * @throws QuestionServiceException
     * 		问题业务异常
     * @return 相关问答
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionRecommends(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException;

    /**
     * 得到标签下的问答列表
     *
     * @param pageNumber
     * 		页数
     * @param pageSize
     * 		页面大小
     * @param tagId
     * 		标签ID
     * @return 分页对象
     * @throws QuestionTagServiceException
     * 		问题标签业务异常
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getQuestionsByTagId(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String tagId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException;

    /**
     * 更新用户昵称
     *
     * @param userId
     * 		用户ID
     */
    void updateQuestionUserNick(java.lang.String userId);
}