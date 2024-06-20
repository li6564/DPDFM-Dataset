package cn.meteor.beyondclouds.modules.question.service;
import cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 问题标签表，用来记录问题里面引用的标签 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IQuestionTagService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.question.entity.QuestionTag> {
    /**
     * 得到问题引用标签
     *
     * @param questionId
     * 		问题ID
     * @return 问题引用标签
     * @throws QuestionTagServiceException
     * 		问题业务异常
     */
    java.util.List<cn.meteor.beyondclouds.modules.tag.entity.Tag> getQuestionTags(java.lang.String questionId) throws cn.meteor.beyondclouds.modules.question.exception.QuestionTagServiceException;

    /**
     * 根据标签ID集合查询相关问题
     *
     * @param page
     * 		分页信息
     * @param tagIds
     * 		标签ID集合
     * @return 相关问题ID分页
     */
    com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> getQuestionIdsByTagIds(com.baomidou.mybatisplus.core.metadata.IPage<java.lang.String> page, java.util.List<java.lang.String> tagIds);
}