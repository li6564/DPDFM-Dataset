package cn.meteor.beyondclouds.modules.question.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * <p>
 * 问题标签表，用来记录问题里面引用的标签 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface QuestionTagMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> {
    /**
     * 通过标签ID集合获取相关的问题ID
     *
     * @param page
     * 		分页
     * @param tagIds
     * 		标签ID集合
     * @return 问题ID的分页信息
     */
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> relevantQuestionIdPage(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, @org.apache.ibatis.annotations.Param("tagIds")
    java.util.List<java.lang.String> tagIds);
}