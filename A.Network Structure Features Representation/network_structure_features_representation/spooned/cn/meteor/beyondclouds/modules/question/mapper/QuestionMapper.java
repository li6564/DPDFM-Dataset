package cn.meteor.beyondclouds.modules.question.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 问题表 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface QuestionMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.question.entity.Question> {
    /**
     * 根据查询条件分页查询问题并获取标签
     *
     * @param page
     * @param queryWrapper
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> selectPageWithTags(com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.question.entity.Question> page, @org.apache.ibatis.annotations.Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
    com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<?> queryWrapper);

    /**
     * 批量查询并获取标签
     *
     * @param questionIds
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.question.entity.Question> listByIdsWithTags(@org.apache.ibatis.annotations.Param("questionIds")
    java.util.List<java.lang.String> questionIds);
}