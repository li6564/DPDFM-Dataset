package cn.meteor.beyondclouds.modules.question.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 问题类别表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IQuestionCategoryService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.question.entity.QuestionCategory> {
    /**
     * 获取全部分类
     *
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionCategory> getCategories();
}