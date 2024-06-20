package cn.meteor.beyondclouds.modules.blog.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
public interface BlogPraiseMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.blog.entity.BlogPraise> {
    /**
     * 查询点赞列表分页
     *
     * @param page
     * @param targetId
     * @param targetType
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> selectPraisePage(com.baomidou.mybatisplus.extension.plugins.pagination.Page<?> page, @org.apache.ibatis.annotations.Param("targetId")
    java.lang.String targetId, @org.apache.ibatis.annotations.Param("targetType")
    java.lang.Integer targetType);
}