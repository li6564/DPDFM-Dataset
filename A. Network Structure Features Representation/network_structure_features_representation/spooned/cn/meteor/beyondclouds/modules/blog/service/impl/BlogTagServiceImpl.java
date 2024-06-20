package cn.meteor.beyondclouds.modules.blog.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 博客标签表，用来记录博客里面引用了哪些标签 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class BlogTagServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.blog.mapper.BlogTagMapper, cn.meteor.beyondclouds.modules.blog.entity.BlogTag> implements cn.meteor.beyondclouds.modules.blog.service.IBlogTagService {
    private cn.meteor.beyondclouds.modules.blog.mapper.BlogTagMapper blogTagMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public BlogTagServiceImpl(cn.meteor.beyondclouds.modules.blog.mapper.BlogTagMapper blogTagMapper) {
        this.blogTagMapper = blogTagMapper;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> getBlogIdByTagId(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String tagId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 获取博客ID
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("tag_id", tagId);
        return page(page, queryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> getRelatedBlogIds(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.util.Collection<java.lang.String> tagIds) {
        return blogTagMapper.selectRelatedBlogIds(page, tagIds);
    }
}