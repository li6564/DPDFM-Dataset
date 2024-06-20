package cn.meteor.beyondclouds.modules.tag.service;
import cn.meteor.beyondclouds.modules.tag.exception.TagServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface ITagService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.tag.entity.Tag> {
    /**
     * 创建标签
     *
     * @param tagName
     * @param tagType
     * @param userId
     * @return TagDetail
     * @throws TagServiceException
     */
    cn.meteor.beyondclouds.modules.tag.dto.TagDetailDTO createTag(java.lang.String tagName, java.lang.Integer tagType, java.lang.String userId) throws cn.meteor.beyondclouds.modules.tag.exception.TagServiceException;

    /**
     * 检索标签
     *
     * @param keywords
     * @param pageNumber
     * @param pageSize
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> searchTags(java.lang.String keywords, java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 我创建的标签列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> getTagPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 热门标签
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> getHotPage(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 增加标签引用次数
     *
     * @param tagId
     * @param count
     */
    void increaseReferenceCount(java.lang.String tagId, int count);

    /**
     * 批量增加标签引用次数
     *
     * @param tagIds
     * @param count
     */
    void increaseReferenceCountBatch(java.util.Collection<java.lang.String> tagIds, int count);

    /**
     * 减少标签引用次数
     *
     * @param tagId
     * @param count
     */
    void decreaseReferenceCount(java.lang.String tagId, int count);

    /**
     * 批量减少标签引用次数
     *
     * @param tagIds
     * @param count
     */
    void decreaseReferenceCountBatch(java.util.Collection<java.lang.String> tagIds, int count);
}