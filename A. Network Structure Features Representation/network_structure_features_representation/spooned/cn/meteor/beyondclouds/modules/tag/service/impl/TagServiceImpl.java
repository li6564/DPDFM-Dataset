package cn.meteor.beyondclouds.modules.tag.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class TagServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.tag.mapper.TagMapper, cn.meteor.beyondclouds.modules.tag.entity.Tag> implements cn.meteor.beyondclouds.modules.tag.service.ITagService {
    // 博客标签
    private static final long TAGTYPE_0 = 0;

    // 问题标签
    private static final long TAGTYPE_2 = 2;

    // 标签类型数量
    private static final long TAGTYPE_NUMBER = 2;

    private cn.meteor.beyondclouds.modules.tag.mapper.TagMapper tagMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setTagMapper(cn.meteor.beyondclouds.modules.tag.mapper.TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.tag.dto.TagDetailDTO createTag(java.lang.String tagName, java.lang.Integer tagType, java.lang.String userId) throws cn.meteor.beyondclouds.modules.tag.exception.TagServiceException {
        // 1. 判断标签类型是否正确
        if ((tagType != cn.meteor.beyondclouds.modules.tag.service.impl.TagServiceImpl.TAGTYPE_0) && (tagType != cn.meteor.beyondclouds.modules.tag.service.impl.TagServiceImpl.TAGTYPE_2)) {
            throw new cn.meteor.beyondclouds.modules.tag.exception.TagServiceException(cn.meteor.beyondclouds.modules.tag.enums.TagErrorCode.TAGTYPE_NOT_EXISTS);
        }
        // 2. 判断是否存在该标签
        cn.meteor.beyondclouds.modules.tag.entity.Tag tags = tagMapper.getTags(tagName, tagType);
        if (tags != null) {
            throw new cn.meteor.beyondclouds.modules.tag.exception.TagServiceException(cn.meteor.beyondclouds.modules.tag.enums.TagErrorCode.TAGTYPE_EXISTS);
        }
        // 3.储存标签
        cn.meteor.beyondclouds.modules.tag.entity.Tag tag = new cn.meteor.beyondclouds.modules.tag.entity.Tag();
        tag.setUserId(userId);
        tag.setTagName(tagName);
        tag.setTagType(tagType);
        save(tag);
        // 4.返回标签id与标签名称
        cn.meteor.beyondclouds.modules.tag.dto.TagDetailDTO tagDetail = new cn.meteor.beyondclouds.modules.tag.dto.TagDetailDTO();
        org.springframework.beans.BeanUtils.copyProperties(tag, tagDetail);
        return tagDetail;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> searchTags(java.lang.String keywords, java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        tagQueryWrapper.like("tag_name", keywords);
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        return page(page, tagQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> getTagPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        tagQueryWrapper.eq("user_id", userId);
        return page(page, tagQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> getHotPage(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.tag.entity.Tag> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagHotQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        tagHotQueryWrapper.orderByDesc("reference_count");
        return page(page, tagHotQueryWrapper);
    }

    @java.lang.Override
    public void increaseReferenceCount(java.lang.String tagId, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        tagUpdateWrapper.eq("tag_id", tagId);
        tagUpdateWrapper.setSql("reference_count=reference_count + " + count);
        update(tagUpdateWrapper);
    }

    @java.lang.Override
    public void increaseReferenceCountBatch(java.util.Collection<java.lang.String> tagIds, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        tagUpdateWrapper.in("tag_id", tagIds);
        tagUpdateWrapper.setSql("reference_count=reference_count + " + count);
        update(tagUpdateWrapper);
    }

    @java.lang.Override
    public void decreaseReferenceCount(java.lang.String tagId, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        tagUpdateWrapper.eq("tag_id", tagId);
        tagUpdateWrapper.setSql("reference_count=reference_count - " + count);
        update(tagUpdateWrapper);
    }

    @java.lang.Override
    public void decreaseReferenceCountBatch(java.util.Collection<java.lang.String> tagIds, int count) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.tag.entity.Tag> tagUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        tagUpdateWrapper.in("tag_id", tagIds);
        tagUpdateWrapper.setSql("reference_count=reference_count - " + count);
        update(tagUpdateWrapper);
    }
}