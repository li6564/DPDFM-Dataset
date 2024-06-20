package cn.meteor.beyondclouds.modules.project.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author gaoTong
 * @date 2020/2/12 10:29
 */
@io.swagger.annotations.Api(tags = "项目分类API")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api")
public class ProjectCategoryApi {
    private cn.meteor.beyondclouds.modules.project.service.IProjectCategoryService projectCategoryService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectCategoryService(cn.meteor.beyondclouds.modules.project.service.IProjectCategoryService projectCategoryService) {
        this.projectCategoryService = projectCategoryService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取项目分类列表")
    @org.springframework.web.bind.annotation.GetMapping("/project/categories")
    public cn.meteor.beyondclouds.core.api.Response<java.util.List<cn.meteor.beyondclouds.modules.project.entity.ProjectCategory>> getProjectCategory() {
        return cn.meteor.beyondclouds.core.api.Response.success(projectCategoryService.getProjectCategory());
    }
}