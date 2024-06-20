package cn.meteor.beyondclouds.modules.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
/**
 * <p>
 * 问题表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class QuestionServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.question.mapper.QuestionMapper, cn.meteor.beyondclouds.modules.question.entity.Question> implements cn.meteor.beyondclouds.modules.question.service.IQuestionService {
    private final java.lang.String QUESTION_END_EN = "?";

    private final java.lang.String QUESTION_END_CN = "？";

    private cn.meteor.beyondclouds.modules.question.service.IQuestionExtService questionExtService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionTagService questionTagService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionCategoryService questionCategoryService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService;

    private cn.meteor.beyondclouds.modules.tag.service.ITagService tagService;

    private cn.meteor.beyondclouds.modules.question.mapper.QuestionMapper questionMapper;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionServiceImpl(cn.meteor.beyondclouds.modules.question.service.IQuestionExtService questionExtService, cn.meteor.beyondclouds.modules.question.service.IQuestionCategoryService questionCategoryService, cn.meteor.beyondclouds.modules.topic.service.ITopicReferenceService topicReferenceService, cn.meteor.beyondclouds.modules.question.mapper.QuestionMapper questionMapper) {
        this.questionExtService = questionExtService;
        this.questionCategoryService = questionCategoryService;
        this.topicReferenceService = topicReferenceService;
        this.questionMapper = questionMapper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionPraiseService(cn.meteor.beyondclouds.modules.question.service.IQuestionPraiseService questionPraiseService) {
        this.questionPraiseService = questionPraiseService;
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
    public void setTopicService(cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService) {
        this.topicService = topicService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService) {
        this.questionReplyService = questionReplyService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionTagService(cn.meteor.beyondclouds.modules.question.service.IQuestionTagService questionTagService) {
        this.questionTagService = questionTagService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyCommentService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService) {
        this.questionReplyCommentService = questionReplyCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setTagService(cn.meteor.beyondclouds.modules.tag.service.ITagService tagService) {
        this.tagService = tagService;
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void postQuestion(cn.meteor.beyondclouds.modules.question.entity.Question question, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> tagIds, java.util.List<java.lang.String> topicIds) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.检查问题标题是否以？结尾
        if ((!question.getQuestionTitle().endsWith(QUESTION_END_EN)) && (!question.getQuestionTitle().endsWith(QUESTION_END_CN))) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_END_ERROR);
        }
        // 2.检查问题分类是否存在
        cn.meteor.beyondclouds.modules.question.entity.QuestionCategory questionCategory = questionCategoryService.getById(question.getCategoryId());
        if (null == questionCategory) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.INCORRECT_CATEGORY);
        }
        // 3.检查问题摘要是否为空，若为空则自动填充
        if (org.springframework.util.StringUtils.isEmpty(question.getQuestionAbstract())) {
            question.setQuestionAbstract(cn.meteor.beyondclouds.util.AbstractUtils.extractWithoutHtml(content, 20));
        }
        // 3.保存问题信息
        question.setCategory(questionCategory.getCategory());
        question.setReplyNumber(0);
        question.setSolved(false);
        question.setViewNumber(0);
        question.setUserNick(userService.getById(question.getUserId()).getNickName());
        save(question);
        // 4.保存问题扩展信息
        cn.meteor.beyondclouds.modules.question.entity.QuestionExt questionExt = new cn.meteor.beyondclouds.modules.question.entity.QuestionExt();
        questionExt.setQuestionId(question.getQuestionId());
        questionExt.setContent(content);
        questionExt.setContentHtml(contentHtml);
        questionExtService.save(questionExt);
        // 5.处理对话题和标签的引用
        updateTopicAndTagReference(question.getUserId(), topicIds, tagIds, question.getQuestionId(), false);
        // 6.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION, question.getQuestionId()));
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void deleteQuestion(java.lang.String questionId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.判断自己是否发布过此问题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("question_id", questionId).eq("user_id", userId).eq("status", 0);
        cn.meteor.beyondclouds.modules.question.entity.Question question = getOne(questionQueryWrapper);
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.删除question-tag表中关于该问题的标签
        deleteOldTagReferences(questionId);
        // 3.删除话题引用表中的问题信息
        deleteOldTopicReferences(questionId);
        // 4.删除关于该问题的所有回复和评论以及对回复的点赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionReplyQueryWrapper.eq("question_id", questionId);
        java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyList = questionReplyService.list(questionReplyQueryWrapper);
        java.util.List<java.lang.String> replyIds = questionReplyList.stream().map(cn.meteor.beyondclouds.modules.question.entity.QuestionReply::getReplyId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(replyIds)) {
            // 删评论
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment> questionReplyCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            questionReplyCommentQueryWrapper.in("reply_id", replyIds);
            questionReplyCommentService.remove(questionReplyCommentQueryWrapper);
            // 删点赞
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            questionPraiseQueryWrapper.in("target_id", replyIds);
            questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_REPLY_PRAISE.getPraiseType());
            questionPraiseService.remove(questionPraiseQueryWrapper);
            // 删回复
            questionReplyService.removeByIds(replyIds);
        }
        // 5.删除question-ext表中的问题信息
        questionExtService.remove(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("question_id", questionId));
        // 6.删除对问题的点赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionPraiseQueryWrapper.eq("target_id", questionId);
        questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_PRAISE.getPraiseType());
        questionPraiseService.remove(questionPraiseQueryWrapper);
        // 7.删除question表中的问题信息
        removeById(questionId);
        // 8.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.deleteMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION, question.getQuestionId()));
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void updateQuestion(cn.meteor.beyondclouds.modules.question.entity.Question question, java.lang.String content, java.lang.String contentHtml, java.util.List<java.lang.String> tagIds, java.util.List<java.lang.String> topicIds) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.判断自己是否发布过此问题
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("question_id", question.getQuestionId()).eq("user_id", question.getUserId()).eq("status", 0);
        cn.meteor.beyondclouds.modules.question.entity.Question questionInDb = getOne(questionQueryWrapper);
        if (null == questionInDb) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 2.检查问题标题是否以？结尾
        if (!org.springframework.util.StringUtils.isEmpty(question.getQuestionTitle())) {
            if ((!question.getQuestionTitle().endsWith(QUESTION_END_EN)) && (!question.getQuestionTitle().endsWith(QUESTION_END_CN))) {
                throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_END_ERROR);
            }
        }
        // 3.检查问题分类是否存在
        if (null != question.getCategoryId()) {
            cn.meteor.beyondclouds.modules.question.entity.QuestionCategory questionCategory = questionCategoryService.getById(question.getCategoryId());
            if (null == questionCategory) {
                throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.INCORRECT_CATEGORY);
            }
            question.setCategory(questionCategory.getCategory());
        }
        // 2.更新问题信息
        updateById(question);
        // 3.更新问题扩展信息
        if (!org.springframework.util.StringUtils.isEmpty(content)) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionExt> updateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            updateWrapper.set("content", content).eq("question_id", question.getQuestionId());
            updateWrapper.set("content_html", contentHtml).eq("question_id", question.getQuestionId());
            questionExtService.update(updateWrapper);
        }
        // 5.处理对话题和标签的引用
        updateTopicAndTagReference(question.getUserId(), topicIds, tagIds, question.getQuestionId(), true);
        // 6.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION, question.getQuestionId()));
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO getQuestionDetail(java.lang.String questionId, cn.meteor.beyondclouds.core.authentication.Subject subject, boolean updateViewNum) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException {
        // 1.获取问题基本信息
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("question_id", questionId);
        questionQueryWrapper.eq("status", 0);
        cn.meteor.beyondclouds.modules.question.entity.Question question = getOne(questionQueryWrapper);
        // 2.判断是否存在此问题
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        // 6.更新问题浏览量
        if (updateViewNum) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            questionUpdateWrapper.set("view_number", question.getViewNumber() + 1).eq("question_id", question.getQuestionId());
            update(questionUpdateWrapper);
        }
        question = getById(questionId);
        // 3.获取问题扩展信息
        cn.meteor.beyondclouds.modules.question.entity.QuestionExt questionExt = questionExtService.getOne(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("question_id", questionId));
        // 4.获取问题标签
        java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> tags = questionTagService.getQuestionTags(questionId);
        if (org.springframework.util.CollectionUtils.isEmpty(tags)) {
            question.setTags(java.util.List.of());
        } else {
            question.setTags(tags);
        }
        // 5.获取话题详情
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> references = topicReferenceService.list(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("referencer_id", question.getQuestionId()));
        java.util.List<java.lang.String> topicIds = references.stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicReference::getTopicId).collect(java.util.stream.Collectors.toList());
        java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topics = new java.util.ArrayList<>();
        if (!org.springframework.util.CollectionUtils.isEmpty(topicIds)) {
            topics = topicService.listByIds(topicIds);
        }
        if (org.springframework.util.CollectionUtils.isEmpty(topics)) {
            question.setTopics(java.util.List.of());
        } else {
            question.setTopics(topics);
        }
        // 7.生成问题详情对象
        cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO questionDetail = new cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO();
        org.springframework.beans.BeanUtils.copyProperties(question, questionDetail);
        questionDetail.setContent(questionExt.getContent());
        questionDetail.setContentHtml(questionExt.getContentHtml());
        // 查看当前用户有没有关注作者
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            questionDetail.setFollowedAuthor(userFollowService.hasFollowedUser(question.getUserId()));
        } else {
            questionDetail.setFollowedAuthor(false);
        }
        // 查看当前用户有没有对该问题点赞
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionPraise> questionPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            questionPraiseQueryWrapper.eq("user_id", subject.getId());
            questionPraiseQueryWrapper.eq("target_id", question.getQuestionId());
            questionPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.question.enums.QuestionPraiseType.QUESTION_PRAISE.getPraiseType());
            cn.meteor.beyondclouds.modules.question.entity.QuestionPraise questionPraise = questionPraiseService.getOne(questionPraiseQueryWrapper);
            questionDetail.setPraised(null != questionPraise);
        } else {
            questionDetail.setPraised(false);
        }
        return questionDetail;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getQuestionPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer categoryId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.orderByDesc("q.priority");
        questionQueryWrapper.orderByDesc("q.create_time");
        questionQueryWrapper.eq("q.status", 0);
        if (null != categoryId) {
            questionQueryWrapper.eq("q.category_id", categoryId);
        }
        return questionMapper.selectPageWithTags(page, questionQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getUserQuestionPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("q.user_id", userId).orderByDesc("q.create_time");
        if (!cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId().equals(userId)) {
            questionQueryWrapper.eq("q.status", 0);
        }
        return questionMapper.selectPageWithTags(page, questionQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getUserParticipatePage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        questionReplyService.participateQuestionIdPage(page, userId);
        questionPage.setTotal(page.getPages());
        questionPage.setSize(page.getSize());
        questionPage.setCurrent(page.getCurrent());
        java.util.List<java.lang.String> questionIds = page.getRecords();
        if (!org.springframework.util.CollectionUtils.isEmpty(questionIds)) {
            questionPage.setRecords(questionMapper.listByIdsWithTags(questionIds));
        } else {
            questionPage.setRecords(java.util.List.of());
        }
        return questionPage;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getHotsQuestionsPage(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionQueryWrapper.eq("q.status", 0);
        questionQueryWrapper.orderByDesc("q.view_number");
        return questionMapper.selectPageWithTags(page, questionQueryWrapper);
    }

    /**
     * 更新问题里面对标签和话题的引用
     *
     * @param userId
     * @param tagIds
     * @param topicIds
     * @param questionId
     * @param update
     */
    private void updateTopicAndTagReference(java.lang.String userId, java.util.List<java.lang.String> topicIds, java.util.List<java.lang.String> tagIds, java.lang.String questionId, boolean update) {
        // 如果用户传的topicIds为[]并且现在在执行修改问答的操作，就删除该问答以前引用的所有话题
        if (org.springframework.util.CollectionUtils.isEmpty(topicIds) && update) {
            deleteOldTopicReferences(questionId);
        }
        // 如果用户传的tagIds为[]并且现在在执行修改问答的操作，就删除该问答以前引用的所有标签
        if (org.springframework.util.CollectionUtils.isEmpty(tagIds) && update) {
            deleteOldTagReferences(questionId);
        }
        // 1.处理话题引用
        if (!org.springframework.util.CollectionUtils.isEmpty(topicIds)) {
            // 获取要引用的话题，不正确的话题ID会被自动过滤
            java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicList = topicService.listByIds(topicIds);
            java.util.List<java.lang.String> existsTopicIds = topicList.stream().map(cn.meteor.beyondclouds.modules.topic.entity.Topic::getTopicId).collect(java.util.stream.Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(existsTopicIds)) {
                if (update) {
                    deleteOldTopicReferences(questionId);
                }
                // 创建引用列表
                java.util.List<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceList = existsTopicIds.stream().map(topicId -> {
                    cn.meteor.beyondclouds.modules.topic.entity.TopicReference topicReference = new cn.meteor.beyondclouds.modules.topic.entity.TopicReference();
                    topicReference.setUserId(userId);
                    topicReference.setTopicId(topicId);
                    topicReference.setReferencerId(questionId);
                    topicReference.setReferencerType(cn.meteor.beyondclouds.modules.topic.enums.TopicReferenceType.QUESTION_REFERENCE.getType());
                    return topicReference;
                }).collect(java.util.stream.Collectors.toList());
                // 保存话题引用
                topicReferenceService.saveBatch(topicReferenceList);
                // 增加话题引用次数
                topicService.increaseReferenceCountBatch(existsTopicIds, 1);
            }
        }
        // 2.处理标签引用
        if (!org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            java.util.List<java.lang.String> existsTagIds = tagService.listByIds(tagIds).stream().map(cn.meteor.beyondclouds.modules.tag.entity.Tag::getTagId).collect(java.util.stream.Collectors.toList());
            if (!org.springframework.util.CollectionUtils.isEmpty(existsTagIds)) {
                if (update) {
                    deleteOldTagReferences(questionId);
                }
                // 保存标签引用
                java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> questionTagList = existsTagIds.stream().map(tagId -> {
                    cn.meteor.beyondclouds.modules.question.entity.QuestionTag questionTag = new cn.meteor.beyondclouds.modules.question.entity.QuestionTag();
                    questionTag.setQuestionId(questionId);
                    questionTag.setTagId(tagId);
                    return questionTag;
                }).collect(java.util.stream.Collectors.toList());
                // 保存标签引用
                questionTagService.saveBatch(questionTagList);
                // 增加标签引用次数
                tagService.increaseReferenceCountBatch(existsTagIds, 1);
            }
        }
    }

    /**
     * 删除问答的旧话题引用
     *
     * @param questionId
     */
    private void deleteOldTopicReferences(java.lang.String questionId) {
        // 删除旧话题引用并更新话题的引用量
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.topic.entity.TopicReference> topicReferenceQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        topicReferenceQueryWrapper.eq("referencer_id", questionId);
        // 减少对应话题的引用数量
        java.util.List<java.lang.String> referencedTopicIds = topicReferenceService.list(topicReferenceQueryWrapper).stream().map(cn.meteor.beyondclouds.modules.topic.entity.TopicReference::getTopicId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(referencedTopicIds)) {
            // 减少话题引用数量
            topicService.decreaseReferenceCountBatch(referencedTopicIds, 1);
        }
        // 删除话题引用
        topicReferenceService.remove(topicReferenceQueryWrapper);
    }

    /**
     * 删除问答的旧标签引用
     *
     * @param questionId
     */
    private void deleteOldTagReferences(java.lang.String questionId) {
        // 删除旧标签引用并更新标签的引用量
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> questionTagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionTagQueryWrapper.eq("question_id", questionId);
        // 减少对应话题的引用数量
        java.util.List<java.lang.String> referencedTagIds = questionTagService.list(questionTagQueryWrapper).stream().map(cn.meteor.beyondclouds.modules.question.entity.QuestionTag::getTagId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(referencedTagIds)) {
            // 减少标签引用数量
            tagService.decreaseReferenceCountBatch(referencedTagIds, 1);
        }
        // 删除标签引用
        questionTagService.remove(questionTagQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionRecommends(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException {
        // 1.查询该问题是否存在
        cn.meteor.beyondclouds.modules.question.entity.Question question = getById(questionId);
        if (null == question) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionErrorCode.QUESTION_NOT_FOUND);
        }
        com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> tagIdsPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 2.得到问题对应的tagIds
        java.util.List<java.lang.String> tagIds = questionTagService.list(cn.meteor.beyondclouds.modules.question.util.QuestionUtils.getWrapper("question_id", question.getQuestionId())).stream().map(cn.meteor.beyondclouds.modules.question.entity.QuestionTag::getTagId).collect(java.util.stream.Collectors.toList());
        // 3.判断tagIds是否为空
        if (!org.springframework.util.CollectionUtils.isEmpty(tagIds)) {
            questionTagService.getQuestionIdsByTagIds(tagIdsPage, tagIds);
            questionPage.setTotal(tagIdsPage.getTotal());
            questionPage.setCurrent(tagIdsPage.getCurrent());
            java.util.List<java.lang.String> questionIds = tagIdsPage.getRecords();
            // 判断questionIds的长度是否为1，若为1，则说明questionIds只有传入的questionId，直接返回空
            if (questionIds.size() != 1) {
                // 移除自身ID
                questionIds.remove(question.getQuestionId());
                questionPage.setRecords(questionMapper.listByIdsWithTags(questionIds));
            } else {
                questionPage.setRecords(java.util.List.of());
            }
        } else {
            questionPage.setRecords(java.util.List.of());
        }
        return questionPage;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> getQuestionsByTagId(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String tagId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException {
        // 1.检查标签是否存在
        cn.meteor.beyondclouds.modules.tag.entity.Tag tag = tagService.getById(tagId);
        if (null == tag) {
            throw new cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException(cn.meteor.beyondclouds.modules.question.enums.QuestionTagErrorCode.TAG_NOT_FOUND);
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> questionTagPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 2.通过标签ID得到对应的问题ID
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> questionTagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionTagQueryWrapper.eq("tag_id", tag.getTagId());
        questionTagQueryWrapper.orderByDesc("create_time");
        questionTagService.page(questionTagPage, questionTagQueryWrapper);
        java.util.List<java.lang.String> questionIds = questionTagPage.getRecords().stream().map(cn.meteor.beyondclouds.modules.question.entity.QuestionTag::getQuestionId).collect(java.util.stream.Collectors.toList());
        questionPage.setTotal(questionTagPage.getTotal());
        questionPage.setCurrent(questionTagPage.getCurrent());
        if (!org.springframework.util.CollectionUtils.isEmpty(questionIds)) {
            questionPage.setRecords(questionMapper.listByIdsWithTags(questionIds));
        } else {
            questionPage.setRecords(java.util.List.of());
        }
        return questionPage;
    }

    @java.lang.Override
    public void updateQuestionUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            questionUpdateWrapper.set("user_nick", user.getNickName()).eq("user_id", user.getUserId());
            update(questionUpdateWrapper);
        }
    }
}