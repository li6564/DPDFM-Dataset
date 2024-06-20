package cn.meteor.beyondclouds.modules.search.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
@lombok.extern.slf4j.Slf4j
public class SearchServiceImpl implements cn.meteor.beyondclouds.modules.search.service.ISearchService {
    private cn.meteor.beyondclouds.modules.search.repository.ISearchRepository searchRepository;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    private cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService searchDegreeService;

    @org.springframework.beans.factory.annotation.Autowired
    public SearchServiceImpl(cn.meteor.beyondclouds.modules.search.repository.ISearchRepository searchRepository, cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService, cn.meteor.beyondclouds.modules.project.service.IProjectService projectService, cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService, cn.meteor.beyondclouds.modules.user.service.IUserService userService, cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.searchRepository = searchRepository;
        this.blogService = blogService;
        this.projectService = projectService;
        this.topicService = topicService;
        this.userService = userService;
        this.questionService = questionService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setSearchDegreeService(cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService searchDegreeService) {
        this.searchDegreeService = searchDegreeService;
    }

    @java.lang.Override
    public void saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) throws java.lang.Exception {
        cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem = getSearchItemInDb(itemType, itemId);
        if (null != searchItem) {
            searchRepository.save(searchItem);
            log.debug("elasticsearch-save:{}", searchItem);
        }
    }

    @java.lang.Override
    public void deleteSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) {
        searchRepository.deleteById(cn.meteor.beyondclouds.modules.search.entity.SearchItemId.of(itemType, itemId));
        log.debug("elasticsearch-delete:{}", cn.meteor.beyondclouds.modules.search.entity.SearchItemId.of(itemType, itemId));
    }

    @java.lang.Override
    public void updateSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) throws java.lang.Exception {
        cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem = getSearchItemInDb(itemType, itemId);
        searchRepository.save(searchItem);
        log.debug("elasticsearch-update:{}", searchItem);
    }

    @java.lang.Override
    public java.util.Optional<cn.meteor.beyondclouds.modules.search.entity.SearchItem> getSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) {
        return searchRepository.findById(cn.meteor.beyondclouds.modules.search.entity.SearchItemId.of(itemType, itemId));
    }

    @java.lang.Override
    public org.springframework.data.domain.Page<cn.meteor.beyondclouds.modules.search.entity.SearchItem> searchPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String itemType, java.lang.String keywords, boolean updateDegree) {
        // 构造搜索条件
        org.elasticsearch.index.query.DisMaxQueryBuilder disMaxQueryBuilder = new org.elasticsearch.index.query.DisMaxQueryBuilder();
        org.elasticsearch.index.query.QueryBuilder titleQueryBuilder = org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery("title", keywords).boost(2.0F);
        org.elasticsearch.index.query.QueryBuilder descriptionQueryBuilder = org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery("description", keywords);
        org.elasticsearch.index.query.QueryBuilder contentQueryBuilder = org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery("content", keywords);
        disMaxQueryBuilder.add(titleQueryBuilder);
        disMaxQueryBuilder.add(descriptionQueryBuilder);
        disMaxQueryBuilder.add(contentQueryBuilder);
        org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder = org.elasticsearch.index.query.QueryBuilders.boolQuery().must(disMaxQueryBuilder);
        if (null != itemType) {
            boolQueryBuilder.must(org.elasticsearch.index.query.QueryBuilders.constantScoreQuery(org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery("itemType", itemType)));
        }
        // 搜索
        org.springframework.data.domain.Page<cn.meteor.beyondclouds.modules.search.entity.SearchItem> searchItemPage = searchRepository.search(boolQueryBuilder, org.springframework.data.domain.PageRequest.of(pageNumber, pageSize));
        // 计算搜索度并保存
        java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchItem> hitItems = searchItemPage.getContent();
        if (updateDegree) {
            calculateTopicDegrees(hitItems, keywords);
        }
        return searchItemPage;
    }

    private void calculateTopicDegrees(java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchItem> hitItems, java.lang.String keywords) {
        java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreeList = new java.util.ArrayList<>();
        for (cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem : hitItems) {
            if (searchItem.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC)) {
                double score = cn.meteor.beyondclouds.modules.search.util.TopicScoreUtils.calculateScore(searchItem.getTitle(), keywords);
                if (score > 0) {
                    cn.meteor.beyondclouds.modules.search.entity.SearchDegree searchDegree = new cn.meteor.beyondclouds.modules.search.entity.SearchDegree();
                    searchDegree.setItemId(searchItem.getItemId());
                    searchDegree.setItemType(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC.name());
                    searchDegree.setDegree(score);
                    searchDegreeList.add(searchDegree);
                }
            }
        }
        if (searchDegreeList.size() > 0) {
            searchDegreeService.updateTopicDegreeBatch(searchDegreeList);
        }
    }

    @java.lang.Override
    public void buildIndex() {
        // 1.建立用户索引
        int current = 1;
        while (true) {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.User> userPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(current, 1000);
            userService.page(userPage);
            java.util.List<cn.meteor.beyondclouds.modules.user.entity.User> userList = userPage.getRecords();
            if (!org.springframework.util.CollectionUtils.isEmpty(userList)) {
                userList.forEach(user -> {
                    try {
                        saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER, user.getUserId());
                    } catch (java.lang.Exception e) {
                        log.error(e.getMessage());
                    }
                });
                current++;
            } else {
                break;
            }
        } 
        // 2.建立项目索引
        current = 1;
        while (true) {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> projectPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(current, 1000);
            projectService.page(projectPage);
            java.util.List<cn.meteor.beyondclouds.modules.project.entity.Project> projectList = projectPage.getRecords();
            if (!org.springframework.util.CollectionUtils.isEmpty(projectList)) {
                projectList.forEach(project -> {
                    try {
                        saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT, java.lang.String.valueOf(project.getProjectId()));
                    } catch (java.lang.Exception e) {
                        log.error(e.getMessage());
                    }
                });
                current++;
            } else {
                break;
            }
        } 
        // 3.建立问答索引
        current = 1;
        while (true) {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> questionPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(current, 1000);
            questionService.page(questionPage);
            java.util.List<cn.meteor.beyondclouds.modules.question.entity.Question> questionList = questionPage.getRecords();
            if (!org.springframework.util.CollectionUtils.isEmpty(questionList)) {
                questionList.forEach(question -> {
                    try {
                        saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION, question.getQuestionId());
                    } catch (java.lang.Exception e) {
                        log.error(e.getMessage());
                    }
                });
                current++;
            } else {
                break;
            }
        } 
        // 4.建立博客索引
        current = 1;
        while (true) {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(current, 1000);
            blogService.page(blogPage);
            java.util.List<cn.meteor.beyondclouds.modules.blog.entity.Blog> questionList = blogPage.getRecords();
            if (!org.springframework.util.CollectionUtils.isEmpty(questionList)) {
                questionList.forEach(blog -> {
                    try {
                        saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG, blog.getBlogId());
                    } catch (java.lang.Exception e) {
                        log.error(e.getMessage());
                    }
                });
                current++;
            } else {
                break;
            }
        } 
        // 4.建立话题索引
        current = 1;
        while (true) {
            com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page(current, 1000);
            topicService.page(topicPage);
            java.util.List<cn.meteor.beyondclouds.modules.topic.entity.Topic> topicList = topicPage.getRecords();
            if (!org.springframework.util.CollectionUtils.isEmpty(topicList)) {
                topicList.forEach(topic -> {
                    try {
                        saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC, topic.getTopicId());
                    } catch (java.lang.Exception e) {
                        log.error(e.getMessage());
                    }
                });
                current++;
            } else {
                break;
            }
        } 
    }

    private cn.meteor.beyondclouds.modules.search.entity.SearchItem getSearchItemInDb(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException, cn.meteor.beyondclouds.modules.user.exception.UserServiceException, cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem = null;
        try {
            switch (itemType) {
                case BLOG :
                    searchItem = buildSearchItemFromBlog(itemId);
                    break;
                case PROJECT :
                    searchItem = buildSearchItemFromProject(itemId);
                    break;
                case USER :
                case USER_STATISTICS :
                    searchItem = buildSearchItemFromUser(itemId);
                    break;
                case QUESTION :
                    searchItem = buildSearchItemFromQuestion(itemId);
                    break;
                case TOPIC :
                    searchItem = buildSearchItemFromTopic(itemId);
                    break;
                default :
                    throw new java.lang.IllegalArgumentException((("该类型不支持搜索：" + itemType.name()) + ":") + itemId);
            }
        } catch (java.lang.Exception e) {
            log.error("搜索条目存储失败:{}", e.getMessage());
            throw e;
        }
        return searchItem;
    }

    private cn.meteor.beyondclouds.modules.search.entity.SearchItem buildSearchItemFromTopic(java.lang.String itemId) {
        cn.meteor.beyondclouds.modules.topic.entity.Topic topic = topicService.getById(itemId);
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(topic.getUserId());
        if (null != topic) {
            cn.meteor.beyondclouds.modules.search.entity.SearchItem<cn.meteor.beyondclouds.modules.search.entity.extra.TopicExtra> searchItem = new cn.meteor.beyondclouds.modules.search.entity.SearchItem<>(cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC, itemId);
            searchItem.setTitle(topic.getTopicName());
            searchItem.setAuthor(user.getNickName());
            searchItem.setContent(topic.getTopicDescrption());
            searchItem.setCover(topic.getCover());
            searchItem.setDescription(topic.getTopicDescrption());
            searchItem.setCreateTime(topic.getCreateTime());
            searchItem.setUpdateTime(topic.getUpdateTime());
            cn.meteor.beyondclouds.modules.search.entity.extra.TopicExtra topicExtra = new cn.meteor.beyondclouds.modules.search.entity.extra.TopicExtra();
            topicExtra.setReferenceCount(topic.getReferenceCount());
            searchItem.setExtra(topicExtra);
            return searchItem;
        }
        return null;
    }

    private cn.meteor.beyondclouds.modules.search.entity.SearchItem buildSearchItemFromProject(java.lang.String projectId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO projectDetail = projectService.getProjectDetail(projectId, cn.meteor.beyondclouds.core.authentication.Subject.anonymous(this.getClass().getName()), false);
        if (null != projectDetail) {
            cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem = new cn.meteor.beyondclouds.modules.search.entity.SearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT, projectId);
            searchItem.setTitle(projectDetail.getProjectName());
            searchItem.setAuthor(projectDetail.getUserNick());
            searchItem.setContent(projectDetail.getContent());
            searchItem.setCover(projectDetail.getCover());
            searchItem.setDescription(projectDetail.getProjectDescription());
            searchItem.setCreateTime(projectDetail.getCreateTime());
            searchItem.setUpdateTime(projectDetail.getUpdateTime());
            return searchItem;
        }
        return null;
    }

    private cn.meteor.beyondclouds.modules.search.entity.SearchItem buildSearchItemFromUser(java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO userInfoDTO = userService.getUserInfo(userId, true);
        if (null != userInfoDTO) {
            cn.meteor.beyondclouds.modules.search.entity.SearchItem<cn.meteor.beyondclouds.modules.search.entity.extra.UserExtra> searchItem = new cn.meteor.beyondclouds.modules.search.entity.SearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER, userId);
            searchItem.setTitle(userInfoDTO.getNickName());
            searchItem.setAuthor(userInfoDTO.getNickName());
            searchItem.setCover(userInfoDTO.getUserAvatar());
            searchItem.setDescription(userInfoDTO.getSignature());
            searchItem.setCreateTime(userInfoDTO.getCreateTime());
            searchItem.setUpdateTime(userInfoDTO.getUpdateTime());
            cn.meteor.beyondclouds.modules.search.entity.extra.UserExtra userExtra = new cn.meteor.beyondclouds.modules.search.entity.extra.UserExtra();
            org.springframework.beans.BeanUtils.copyProperties(userInfoDTO.getStatistics(), userExtra);
            searchItem.setExtra(userExtra);
            return searchItem;
        }
        return null;
    }

    private cn.meteor.beyondclouds.modules.search.entity.SearchItem buildSearchItemFromQuestion(java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException {
        cn.meteor.beyondclouds.modules.question.dto.QuestionDetailDTO questionDetail = questionService.getQuestionDetail(questionId, cn.meteor.beyondclouds.core.authentication.Subject.anonymous(this.getClass().getName()), false);
        if (null != questionDetail) {
            cn.meteor.beyondclouds.modules.search.entity.SearchItem<cn.meteor.beyondclouds.modules.search.entity.extra.QuestionExtra> searchItem = new cn.meteor.beyondclouds.modules.search.entity.SearchItem<>(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION, questionId);
            searchItem.setTitle(questionDetail.getQuestionTitle());
            searchItem.setAuthor(questionDetail.getUserNick());
            searchItem.setContent(questionDetail.getContent());
            searchItem.setDescription(questionDetail.getQuestionAbstract());
            searchItem.setCreateTime(questionDetail.getCreateTime());
            searchItem.setUpdateTime(questionDetail.getUpdateTime());
            cn.meteor.beyondclouds.modules.search.entity.extra.QuestionExtra questionExtra = new cn.meteor.beyondclouds.modules.search.entity.extra.QuestionExtra();
            questionExtra.setReplyNumber(questionDetail.getReplyNumber());
            questionExtra.setSolved(questionDetail.getSolved());
            searchItem.setExtra(questionExtra);
            return searchItem;
        }
        return null;
    }

    private cn.meteor.beyondclouds.modules.search.entity.SearchItem buildSearchItemFromBlog(java.lang.String blogId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException {
        cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO blogDetail = blogService.getBlog(blogId, cn.meteor.beyondclouds.core.authentication.Subject.anonymous(this.getClass().getName()), false);
        if (null != blogDetail) {
            cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem = new cn.meteor.beyondclouds.modules.search.entity.SearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG, blogId);
            searchItem.setTitle(blogDetail.getBlogTitle());
            searchItem.setAuthor(blogDetail.getUserNick());
            searchItem.setContent(blogDetail.getContent());
            searchItem.setCover(blogDetail.getCover());
            searchItem.setDescription(blogDetail.getBlogAbstract());
            searchItem.setCreateTime(blogDetail.getCreateTime());
            searchItem.setUpdateTime(blogDetail.getUpdateTime());
            return searchItem;
        }
        return null;
    }
}