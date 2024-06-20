package cn.meteor.beyondclouds.modules.user.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author 段启岩
 * @since 2020-01-18
 */
public interface UserMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.user.entity.User> {
    /**
     * 查询热门博主
     *
     * @param page
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> selectHotBloggerPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<cn.meteor.beyondclouds.modules.user.entity.User> page);

    /**
     * 批量查询用户-带统计信息
     *
     * @param userIds
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> listByIdsWithStatistics(@org.apache.ibatis.annotations.Param("userIds")
    java.util.List<java.lang.String> userIds);

    /**
     * 获取活跃用户
     *
     * @param page
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> selectActivesUserPage(com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> page);

    /**
     * 获取精英用户
     *
     * @param objectPage
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> selectEliteUserPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<java.lang.Object> objectPage);

    /**
     * 热门答主
     *
     * @param objectPage
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> selectHotReplierPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<java.lang.Object> objectPage);
}