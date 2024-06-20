package cn.meteor.beyondclouds.modules.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
/**
 * <p>
 * 问题回复评论表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class QuestionReplyCommentServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.question.mapper.QuestionReplyCommentMapper, cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> implements cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService) {
        this.questionReplyService = questionReplyService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionService(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.questionService = questionService;
    }

    @java.lang.Override
    public void publishReplyComment(java.lang.String userId, java.lang.String replyId, java.lang.String parentId, java.lang.String comment) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException {
        org.springframework.util.Assert.notNull(replyId, "replyId must not be null");
        org.springframework.util.Assert.hasText(comment, "comment must not be empty");
        // 1.判断是否存在该回复
        cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = questionReplyService.getById(replyId);
        // 若回复为空，则抛出回复不存在异常
        if (questionReply == null) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyErrorCode.REPLY_NOT_FOUND);
        }
        cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment parentQuestionReplyComment = null;
        // 2.判断父评论是否存在
        if (!org.springframework.util.StringUtils.isEmpty(parentId)) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> questionReplyCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            questionReplyCommentQueryWrapper.eq("reply_id", replyId).eq("comment_id", parentId);
            parentQuestionReplyComment = getOne(questionReplyCommentQueryWrapper);
            // 若父评论为空，则抛出父评论不存在异常
            if (null == parentQuestionReplyComment) {
                throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
            }
        }
        // 3.保存评论信息
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment questionReplyComment = new cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment();
        questionReplyComment.setUserId(userId);
        questionReplyComment.setUserNick(user.getNickName());
        questionReplyComment.setUserAvatar(user.getUserAvatar());
        questionReplyComment.setReplyId(replyId);
        questionReplyComment.setParentId(parentId);
        questionReplyComment.setComment(comment);
        save(questionReplyComment);
        // 4.更新评论层级和评论路径
        if (null != parentQuestionReplyComment) {
            // 子级评论
            questionReplyComment.setDepth(parentQuestionReplyComment.getDepth() + 1);
            questionReplyComment.setThread((parentQuestionReplyComment.getThread() + "/") + questionReplyComment.getCommentId());
        } else {
            // 1级评论
            questionReplyComment.setDepth(0);
            questionReplyComment.setThread("/" + questionReplyComment.getCommentId());
        }
        updateById(questionReplyComment);
    }

    @java.lang.Override
    public void deleteReplyComment(java.lang.String userId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException {
        org.springframework.util.Assert.notNull(commentId, "commentId must not be null");
        org.springframework.util.Assert.notNull(userId, "userId must not be null");
        // 1.判断是否存在该评论
        cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment questionReplyComment = getById(commentId);
        if (null == questionReplyComment) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyCommentErrorCode.COMMENT_NOT_FOUND);
        }
        // 2.判断用户是否有权限删除该评论
        if (!questionReplyComment.getUserId().equals(userId)) {
            // 用户未发表过该评论，判断用户是否为该回复或者该问题的发布者
            // 得到评论对应的回复对象
            cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = questionReplyService.getById(questionReplyComment.getReplyId());
            // 得到回复对应的问题对象
            cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionReply.getQuestionId());
            // 判断用户是否为该回复或该问题的发布者
            if ((!question.getUserId().equals(userId)) && (!questionReply.getUserId().equals(userId))) {
                throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyCommentErrorCode.NO_DELETE_PRIVILEGES);
            }
        }
        // 3.删除评论及其子评论
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> questionReplyCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionReplyCommentQueryWrapper.like("thread", questionReplyComment.getThread());
        remove(questionReplyCommentQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> getReplyCommentPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String replyId, java.lang.String parentId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException {
        org.springframework.util.Assert.notNull(replyId, "replyId must not be null");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 如果parentId为空，则只获取一级评论
        if (null == parentId) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> questionReplyCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            questionReplyCommentQueryWrapper.eq("reply_id", replyId).eq("depth", 0).orderByDesc("create_time");
            return page(page, questionReplyCommentQueryWrapper);
        }
        // 如果parentId不为null，则获取其子评论
        // 判断父评论是否存在
        cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment questionReplyComment = getById(parentId);
        if (null == questionReplyComment) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionReplyCommentServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionReplyCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
        }
        return page(page, cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("parent_id", parentId));
    }

    @java.lang.Override
    public void updateQuestionReplyCommentUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> replyCommentUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            replyCommentUpdateWrapper.set("user_nick", user.getNickName()).eq("user_id", user.getUserId());
            update(replyCommentUpdateWrapper);
        }
    }

    @java.lang.Override
    public void updateQuestionReplyCommentUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> replyCommentUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            replyCommentUpdateWrapper.set("user_avatar", user.getUserAvatar()).eq("user_id", user.getUserId());
            update(replyCommentUpdateWrapper);
        }
    }
}