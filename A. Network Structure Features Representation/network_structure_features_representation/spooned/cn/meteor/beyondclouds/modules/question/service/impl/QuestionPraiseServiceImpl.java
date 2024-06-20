package cn.meteor.beyondclouds.modules.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
@org.springframework.stereotype.Service
public class QuestionPraiseServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.question.mapper.QuestionPraiseMapper, cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> implements cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.question.mapper.QuestionPraiseMapper questionPraiseMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionService(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.questionService = questionService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService) {
        this.questionReplyService = questionReplyService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionPraiseMapper(cn.meteor.beyondclouds.modules.question.mapper.QuestionPraiseMapper questionPraiseMapper) {
        this.questionPraiseMapper = questionPraiseMapper;
    }

    @java.lang.Override
    public void questionPraise(java.lang.String currentUserId, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.查找问题是否存在
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionId);
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.查看是否已经点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionPraiseQueryWrapper.eq("user_id", currentUserId);
        questionPraiseQueryWrapper.eq("target_id", questionId);
        questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.question.entity.QuestionPraise questionPraise = getOne(questionPraiseQueryWrapper);
        if (null != questionPraise) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.ALREADY_PRAISED);
        }
        // 3.保存点赞
        questionPraise = new cn.meteor.beyondclouds.modules.question.entity.QuestionPraise();
        questionPraise.setUserId(currentUserId);
        questionPraise.setTargetId(questionId);
        questionPraise.setTargetType(cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_PRAISE.getPraiseType());
        save(questionPraise);
        // 更新获赞数量
        question.setPraiseNum(question.getPraiseNum() + 1);
        questionService.updateById(question);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_PRAISE, questionPraise.getPraiseId()));
    }

    @java.lang.Override
    public void deleteQuestionPraise(java.lang.String currentUserId, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getById(questionId);
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 1.判断是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionPraiseQueryWrapper.eq("user_id", currentUserId);
        questionPraiseQueryWrapper.eq("target_id", questionId);
        questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.question.entity.QuestionPraise questionPraise = getOne(questionPraiseQueryWrapper);
        if (null == questionPraise) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.NO_PRAISE);
        }
        // 2.删除点赞
        remove(questionPraiseQueryWrapper);
        // 更新获赞数量
        question.setPraiseNum(question.getPraiseNum() - 1);
        questionService.updateById(question);
    }

    @java.lang.Override
    public void questionReplyPraise(java.lang.String currentUserId, java.lang.String replyId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.查找问题回答是否存在
        cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = questionReplyService.getById(replyId);
        if (null == questionReply) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_REPLY_NOT_FOUND);
        }
        // 2.查看是否已经点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionPraiseQueryWrapper.eq("user_id", currentUserId);
        questionPraiseQueryWrapper.eq("target_id", replyId);
        questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_REPLY_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.question.entity.QuestionPraise questionPraise = getOne(questionPraiseQueryWrapper);
        if (null != questionPraise) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.ALREADY_PRAISED);
        }
        // 3.保存点赞
        questionPraise = new cn.meteor.beyondclouds.modules.question.entity.QuestionPraise();
        questionPraise.setUserId(currentUserId);
        questionPraise.setTargetId(replyId);
        questionPraise.setTargetType(cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_REPLY_PRAISE.getPraiseType());
        save(questionPraise);
    }

    @java.lang.Override
    public void deleteQuestionReplyPraise(java.lang.String currentUserId, java.lang.String replyId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.判断是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionPraiseQueryWrapper.eq("user_id", currentUserId);
        questionPraiseQueryWrapper.eq("target_id", replyId);
        questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_REPLY_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.question.entity.QuestionPraise questionPraise = getOne(questionPraiseQueryWrapper);
        if (null == questionPraise) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.NO_PRAISE);
        }
        // 2.删除点赞
        remove(questionPraiseQueryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getQuestionPraises(java.lang.Integer page, java.lang.Integer size, java.lang.String questionId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> praiseUserDTOPage = questionPraiseMapper.selectPraisePage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page(page, size), questionId, cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(praiseUserDTOPage, pageDTO);
        pageDTO.setDataList(praiseUserDTOPage.getRecords());
        return pageDTO;
    }
}