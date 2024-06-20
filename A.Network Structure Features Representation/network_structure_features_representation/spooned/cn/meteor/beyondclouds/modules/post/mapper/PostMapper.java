package cn.meteor.beyondclouds.modules.post.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 动态表 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface PostMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.post.entity.Post> {
    /**
     * 查询推荐动态
     *
     * @param page
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> selectRecommendPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.post.entity.Post> page);

    /**
     * 查询推荐动态
     *
     * @param page
     * @param topicId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.post.entity.Post> selectRecommendPageInTopic(com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.post.entity.Post> page, @org.apache.ibatis.annotations.Param("topicId")
    java.lang.String topicId);
}