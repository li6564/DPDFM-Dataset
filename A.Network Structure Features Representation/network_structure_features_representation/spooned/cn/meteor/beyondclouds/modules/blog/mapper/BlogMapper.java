package cn.meteor.beyondclouds.modules.blog.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface BlogMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> {
    /**
     * 根据查询条件分页查询博客获取标签
     *
     * @param page
     * @param queryWrapper
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> selectPageWithTags(com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.blog.entity.Blog> page, @org.apache.ibatis.annotations.Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
    com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<?> queryWrapper);

    /**
     * 根据查询条件分页查询博客获取标签
     *
     * @param blogIds
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.blog.entity.Blog> listByIdsWithTags(@org.apache.ibatis.annotations.Param("blogIds")
    java.util.Collection<java.lang.String> blogIds);

    /**
     * 查询用户所有博客的浏览量
     *
     * @param userId
     * @return  */
    long selectAllViewCount(@org.apache.ibatis.annotations.Param("userId")
    java.lang.String userId);
}