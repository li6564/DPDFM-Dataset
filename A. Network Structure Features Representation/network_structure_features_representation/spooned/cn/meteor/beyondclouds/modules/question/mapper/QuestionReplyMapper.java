package cn.meteor.beyondclouds.modules.question.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Select;
/**
 * <p>
 * 问题回复表 Mapper 接口
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface QuestionReplyMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> {
    /**
     * 获取参与的问题ID的分页
     *
     * @param page
     * @param userId
     * @return  */
    @org.apache.ibatis.annotations.Select("select DISTINCT(question_id) from (SELECT * from question_reply where user_id=#{userId} order by create_time desc) t")
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> participateQuestionIdPage(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.lang.String userId);
}