package cn.meteor.beyondclouds.modules.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
/**
 * <p>
 * 问题回复表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class QuestionReplyServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.question.mapper.QuestionReplyMapper, cn.meteor.beyondclouds.modules.question.entity.QuestionReply> implements cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService {
    private int ADOPTED_REPLY_STATUS = 1;

    private int NOT_ADOPTED_REPLY_STATUS = 0;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService;

    private cn.meteor.beyondclouds.modules.question.mapper.QuestionReplyMapper questionReplyMapper;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionPraiseService(cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService) {
        this.questionPraiseService = questionPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionService(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.questionService = questionService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyMapper(cn.meteor.beyondclouds.modules.question.mapper.QuestionReplyMapper questionReplyMapper) {
        this.questionReplyMapper = questionReplyMapper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyCommentService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService) {
        this.questionReplyCommentService = questionReplyCommentService;
    }

    @java.lang.Override
    public void replyQuestion(java.lang.String questionId, java.lang.String reply, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException {
        // 1.判断问题是否存在
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionId);
        // 如果问题不存在，则抛出异常
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.保存回复信息
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = new cn.meteor.beyondclouds.modules.question.entity.QuestionReply();
        questionReply.setQuestionId(questionId);
        questionReply.setReply(cn.meteor.beyondclouds.util.CommentUtils.encodeComment(reply));
        questionReply.setUserId(userId);
        questionReply.setUserNick(user.getNickName());
        questionReply.setUserAvatar(user.getUserAvatar());
        // 默认该回复未被采纳
        questionReply.setReplyStatus(0);
        save(questionReply);
        // 更新问题的回复数量
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        questionUpdateWrapper.set("reply_number", question.getReplyNumber() + 1).eq("question_id", questionId);
        questionService.update(questionUpdateWrapper);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY, questionReply.getReplyId()));
    }

    @java.lang.Override
    public void adoptReply(java.lang.String questionId, java.lang.String replyId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException {
        // 1.判断用户是否发布过该问题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("user_id", userId).eq("question_id", questionId);
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getOne(questionQueryWrapper);
        // 若问题不存在，则抛出问题不存在异常
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.判断该问题的回复是否存在
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionReplyQueryWrapper.eq("question_id", questionId).eq("reply_id", replyId);
        cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = getOne(questionReplyQueryWrapper);
        // 若该回复不存在，则抛出回复不存在异常
        if (null == questionReply) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.REPLY_NOT_FOUND);
        }
        // 3.判断该回复之前是否已被采纳，若已被采纳，则抛出回复已被采纳异常
        if (1 == questionReply.getReplyStatus()) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.REPLY_ADOPTED);
        }
        // 4.采纳回复
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> updateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        updateWrapper.set("reply_status", ADOPTED_REPLY_STATUS).eq("reply_id", replyId);
        update(updateWrapper);
        // 5.更新问题状态
        if (!question.getSolved()) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            questionUpdateWrapper.set("solved", true).eq("question_id", questionId);
            questionService.update(questionUpdateWrapper);
        }
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY_ACCEPT, questionReply.getReplyId()));
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> getReplies(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException {
        // 1.判断该问题是否存在
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getOne(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("question_id", questionId));
        // 若问题不存在，则抛出问题不存在异常
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.得到页面信息
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionReplyQueryWrapper.eq("question_id", questionId);
        questionReplyQueryWrapper.orderByDesc("create_time");
        return page(questionReplyPage, questionReplyQueryWrapper);
    }

    @java.lang.Override
    public void deleteReply(java.lang.String replyId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException {
        // 1.判断该回复是否存在
        cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = getById(replyId);
        if (null == questionReply) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.REPLY_NOT_FOUND);
        }
        // 2.判断用户是否有权限删除该回复
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionReply.getQuestionId());
        if (!questionReply.getUserId().equals(userId)) {
            if (!question.getUserId().equals(userId)) {
                throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.NO_DELETE_PRIVILEGES);
            }
        }
        // 3.删除该回复及评论和点赞
        // 删点赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionPraiseQueryWrapper.eq("target_id", replyId);
        questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_REPLY_PRAISE.getPraiseType());
        questionPraiseService.remove(questionPraiseQueryWrapper);
        // 删评论
        questionReplyCommentService.remove(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("reply_id", replyId));
        // 删回复
        removeById(replyId);
        // 4.更新问题的回复数量
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        questionUpdateWrapper.set("reply_number", question.getReplyNumber() - 1).eq("question_id", question.getQuestionId());
        questionService.update(questionUpdateWrapper);
        // 5.更新问题的状态,如果删除回复后问题的采纳回复数为0且问题之前为已解决状态，则需要将其变为未解决
        if (1 == questionReply.getReplyStatus()) {
            // 查询数据库中该问题已采纳回复的数目
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            questionReplyQueryWrapper.eq("question_id", question.getQuestionId()).eq("reply_status", 1);
            java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplies = list(questionReplyQueryWrapper);
            if ((questionReplies.size() == 0) && question.getSolved()) {
                com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> solvedUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
                solvedUpdateWrapper.set("solved", false).eq("question_id", question.getQuestionId());
                questionService.update(solvedUpdateWrapper);
            }
        }
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> participateQuestionIdPage(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.lang.String userId) {
        return questionReplyMapper.participateQuestionIdPage(page, userId);
    }

    @java.lang.Override
    public void updateQuestionReplyUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            questionReplyUpdateWrapper.set("user_nick", user.getNickName()).eq("user_id", user.getUserId());
            update(questionReplyUpdateWrapper);
        }
    }

    @java.lang.Override
    public void updateQuestionReplyUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            questionReplyUpdateWrapper.set("user_avatar", user.getUserAvatar()).eq("user_id", user.getUserId());
            update(questionReplyUpdateWrapper);
        }
    }

    @java.lang.Override
    public void cancelReplyAdoption(java.lang.String questionId, java.lang.String replyId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException {
        // 1.判断用户是否发布过该问题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("user_id", userId).eq("question_id", questionId);
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getOne(questionQueryWrapper);
        // 若问题不存在，则抛出问题不存在异常
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.判断该问题的回复是否存在
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionReplyQueryWrapper.eq("question_id", questionId).eq("reply_id", replyId);
        cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = getOne(questionReplyQueryWrapper);
        // 若该回复不存在，则抛出回复不存在异常
        if (null == questionReply) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.REPLY_NOT_FOUND);
        }
        // 3.判断该回复之前是否已被采纳，若未被采纳，则抛出回复未被采纳异常
        if (0 == questionReply.getReplyStatus()) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.REPLY_NOT_ADOPTED);
        }
        // 4.取消采纳回复
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> updateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        updateWrapper.set("reply_status", NOT_ADOPTED_REPLY_STATUS).eq("reply_id", questionReply.getReplyId());
        update(updateWrapper);
        // 5.更新问题解决状态
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> replyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        replyQueryWrapper.eq("question_id", question.getQuestionId()).eq("reply_status", ADOPTED_REPLY_STATUS);
        java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplies = list(replyQueryWrapper);
        // 若questionReplies为空，则需要更新问题的解决状态
        if (org.springframework.util.CollectionUtils.isEmpty(questionReplies)) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            questionUpdateWrapper.set("solved", false).eq("question_id", question.getQuestionId());
            questionService.update(questionUpdateWrapper);
        }
    }
}