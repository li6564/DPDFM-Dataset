package cn.meteor.beyondclouds.modules.content.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
/**
 * <p>
 * CMS-内容表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-01
 */
@org.springframework.stereotype.Service
public class ContentServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.content.mapper.ContentMapper, cn.meteor.beyondclouds.modules.content.entity.Content> implements cn.meteor.beyondclouds.modules.content.service.IContentService {
    private cn.meteor.beyondclouds.modules.content.service.IContentExtService contentExtService;

    @org.springframework.beans.factory.annotation.Autowired
    public ContentServiceImpl(cn.meteor.beyondclouds.modules.content.service.IContentExtService contentExtService) {
        this.contentExtService = contentExtService;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.content.entity.Content> getPageByChannelId(java.lang.Integer channelId, java.lang.Integer contentType, java.lang.Integer pageNumber, java.lang.Integer pageSize) throws cn.meteor.beyondclouds.modules.content.exception.ContentServiceException {
        org.springframework.util.Assert.notNull(channelId, "channelId must not be null");
        if (cn.meteor.beyondclouds.modules.content.enums.CmsContentType.valueOf(contentType) == null) {
            throw new cn.meteor.beyondclouds.modules.content.exception.ContentServiceException(cn.meteor.beyondclouds.modules.content.enums.ContentErrorCode.CONTENT_TYPE_ERROR);
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.content.entity.Content> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.content.entity.Content> contentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        contentQueryWrapper.eq("channel_id", channelId);
        contentQueryWrapper.eq("content_type", contentType);
        contentQueryWrapper.orderByAsc("priority");
        contentQueryWrapper.orderByDesc("create_time");
        return page(page, contentQueryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.content.dto.ContentDetailDTO getContentDetail(java.lang.Integer contentId) throws cn.meteor.beyondclouds.modules.content.exception.ContentServiceException {
        org.springframework.util.Assert.notNull(contentId, "contentId must not be null");
        // 1. 获取content
        cn.meteor.beyondclouds.modules.content.entity.Content content = getById(contentId);
        if (null == content) {
            throw new cn.meteor.beyondclouds.modules.content.exception.ContentServiceException(cn.meteor.beyondclouds.modules.content.enums.ContentErrorCode.CONTENT_NOT_FOUND);
        }
        // 2. 获取contentExt
        cn.meteor.beyondclouds.modules.content.entity.ContentExt contentExt = contentExtService.getById(contentId);
        // 3. 组合数据
        cn.meteor.beyondclouds.modules.content.dto.ContentDetailDTO contentDetail = new cn.meteor.beyondclouds.modules.content.dto.ContentDetailDTO();
        org.springframework.beans.BeanUtils.copyProperties(content, contentDetail);
        if (null != contentExt) {
            contentDetail.setContent(contentExt.getContent());
        }
        return contentDetail;
    }
}