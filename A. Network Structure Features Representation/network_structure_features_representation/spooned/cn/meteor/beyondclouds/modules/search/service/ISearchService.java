package cn.meteor.beyondclouds.modules.search.service;
import org.springframework.data.domain.Page;
/**
 *
 * @author meteor
 */
public interface ISearchService {
    /**
     * 添加搜索条目
     *
     * @param dataItemType
     * @param itemId
     */
    void saveSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType dataItemType, java.lang.String itemId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException, cn.meteor.beyondclouds.modules.user.exception.UserServiceException, cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException, java.lang.Exception;

    /**
     * 删除搜索条目
     *
     * @param dataItemType
     * @param itemId
     */
    void deleteSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType dataItemType, java.lang.String itemId);

    /**
     * 更新搜索条目
     *
     * @param dataItemType
     * @param itemId
     */
    void updateSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType dataItemType, java.lang.String itemId) throws cn.meteor.beyondclouds.modules.blog.exception.BlogServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException, cn.meteor.beyondclouds.modules.question.exception.QuestionServiceException, cn.meteor.beyondclouds.modules.user.exception.UserServiceException, cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException, java.lang.Exception;

    /**
     * 查找搜索条目
     *
     * @param dataItemType
     * @param itemId
     * @return  */
    java.util.Optional<cn.meteor.beyondclouds.modules.search.entity.SearchItem> getSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType dataItemType, java.lang.String itemId);

    /**
     * 根据关键词分页搜索
     *
     * @param pageNumber
     * @param pageSize
     * @param itemType
     * @param keywords
     * @param updateDegree
     * @return  */
    org.springframework.data.domain.Page<cn.meteor.beyondclouds.modules.search.entity.SearchItem> searchPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String itemType, java.lang.String keywords, boolean updateDegree);

    /**
     * 建立索引
     */
    void buildIndex();
}