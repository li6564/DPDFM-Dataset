package cn.meteor.beyondclouds.modules.blog.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * <p>
 * 博客标签表，用来记录博客里面引用了哪些标签 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface BlogTagMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.blog.entity.BlogTag> {
    /**
     * 根据标签ID获取相关的博客ids
     *
     * @param page
     * @param tagIds
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> selectRelatedBlogIds(com.baomidou.mybatisplus.core.metadata.IPage<?> page, @org.apache.ibatis.annotations.Param("tagIds")
    java.util.Collection<java.lang.String> tagIds);
}