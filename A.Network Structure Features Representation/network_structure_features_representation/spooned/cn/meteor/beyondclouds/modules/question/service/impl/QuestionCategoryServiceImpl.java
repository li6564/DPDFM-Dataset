package cn.meteor.beyondclouds.modules.question.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 问题类别表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class QuestionCategoryServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.question.mapper.QuestionCategoryMapper, cn.meteor.beyondclouds.modules.question.entity.QuestionCategory> implements cn.meteor.beyondclouds.modules.question.service.IQuestionCategoryService {
    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionCategory> getCategories() {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionCategory> questionCategoryQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        questionCategoryQueryWrapper.orderByDesc("priority");
        questionCategoryQueryWrapper.orderByAsc("category_id");
        return list(questionCategoryQueryWrapper);
    }
}