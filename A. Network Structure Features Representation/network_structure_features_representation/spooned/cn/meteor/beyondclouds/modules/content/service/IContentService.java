package cn.meteor.beyondclouds.modules.content.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * CMS-内容表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-01
 */
public interface IContentService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.content.entity.Content> {
    /**
     * 根据栏目ID分页获取内容列表
     *
     * @param channelId
     * @param contentType
     * @param pageNumber
     * @param pageSize
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.content.entity.Content> getPageByChannelId(java.lang.Integer channelId, java.lang.Integer contentType, java.lang.Integer pageNumber, java.lang.Integer pageSize) throws cn.meteor.beyondclouds.modules.content.exception.ContentServiceException;

    /**
     * 根据contentId获取内容详情
     *
     * @param contentId
     * @return  */
    cn.meteor.beyondclouds.modules.content.dto.ContentDetailDTO getContentDetail(java.lang.Integer contentId) throws cn.meteor.beyondclouds.modules.content.exception.ContentServiceException;
}