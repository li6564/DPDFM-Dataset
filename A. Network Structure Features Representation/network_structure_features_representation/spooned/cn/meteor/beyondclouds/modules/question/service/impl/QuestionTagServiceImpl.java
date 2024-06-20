package cn.meteor.beyondclouds.modules.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
/**
 * <p>
 * 问题标签表，用来记录问题里面引用的标签 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class QuestionTagServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.question.mapper.QuestionTagMapper, cn.meteor.beyondclouds.modules.question.entity.QuestionTag> implements cn.meteor.beyondclouds.modules.question.service.IQuestionTagService {
    private cn.meteor.beyondclouds.modules.tag.service.ITagService tagService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.mapper.QuestionMapper questionMapper;

    private cn.meteor.beyondclouds.modules.question.mapper.QuestionTagMapper questionTagMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionTagServiceImpl(cn.meteor.beyondclouds.modules.tag.service.ITagService tagService, cn.meteor.beyondclouds.modules.question.mapper.QuestionMapper questionMapper, cn.meteor.beyondclouds.modules.question.mapper.QuestionTagMapper questionTagMapper) {
        this.tagService = tagService;
        this.questionMapper = questionMapper;
        this.questionTagMapper = questionTagMapper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionService(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.questionService = questionService;
    }

    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> getQuestionTags(java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException {
        // 1.获取问题信息
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("question_id", questionId);
        questionQueryWrapper.eq("status", 0);
        cn.meteor.beyondclouds.modules.question.entity.Question question = questionService.getOne(questionQueryWrapper);
        // 判断该问题是否存在
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.得到问题引用的所有标签的ID
        java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> questionTags = list(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("question_id", questionId));
        java.util.List<java.lang.String> tagIds = questionTags.stream().map(cn.meteor.beyondclouds.modules.question.entity.QuestionTag::getTagId).collect(java.util.stream.Collectors.toList());
        // 3.根据tagIds查询出标签并返回
        if (org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            return java.util.List.of();
        } else {
            return tagService.listByIds(tagIds);
        }
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> getQuestionIdsByTagIds(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.util.List<java.lang.String> tagIds) {
        return questionTagMapper.relevantQuestionIdPage(page, tagIds);
    }
}