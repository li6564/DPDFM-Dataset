package cn.meteor.beyondclouds.modules.question.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author 胡学良
 * @since 2020/2/12
 */
@io.swagger.annotations.Api(tags = "问题类别Api")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api")
public class QuestionCategoryApi {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionCategoryService questionCategoryService;

    @org.springframework.beans.factory.annotation.Autowired
    public QuestionCategoryApi(cn.meteor.beyondclouds.modules.question.service.IQuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("问题全部分类")
    @org.springframework.web.bind.annotation.GetMapping("/question/categories")
    public cn.meteor.beyondclouds.core.api.Response questionCategories() {
        java.util.List<cn.meteor.beyondclouds.modules.question.entity.QuestionCategory> questionCategories = questionCategoryService.getCategories();
        return cn.meteor.beyondclouds.core.api.Response.success(questionCategories);
    }
}