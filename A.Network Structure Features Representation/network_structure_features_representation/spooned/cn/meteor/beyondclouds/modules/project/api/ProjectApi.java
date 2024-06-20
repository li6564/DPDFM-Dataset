package cn.meteor.beyondclouds.modules.project.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 * 项目接口
 *
 * @author 段启岩
 */
@io.swagger.annotations.Api(tags = "项目API")
@cn.meteor.beyondclouds.modules.project.api.RestController
@cn.meteor.beyondclouds.modules.project.api.RequestMapping("/api")
public class ProjectApi {
    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    @org.springframework.beans.factory.annotation.Autowired
    public ProjectApi(cn.meteor.beyondclouds.modules.project.service.IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 发布项目
     *
     * @param projectForm
     * @param bindingResult
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("发布项目")
    @cn.meteor.beyondclouds.modules.project.api.PostMapping("/project")
    public cn.meteor.beyondclouds.core.api.Response publishProject(@cn.meteor.beyondclouds.modules.project.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.project.form.ProjectForm projectForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        // 将项目表单转换为项目对象
        cn.meteor.beyondclouds.modules.project.entity.Project project = new cn.meteor.beyondclouds.modules.project.entity.Project();
        org.springframework.beans.BeanUtils.copyProperties(projectForm, project);
        // 设置用户ID（项目发布者ID）
        project.setUserId(((java.lang.String) (subject.getId())));
        // 发布项目
        try {
            projectService.publishProject(project, projectForm.getContent(), projectForm.getContentHtml());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 发布项目
     *
     * @param projectForm
     * @param bindingResult
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("修改项目")
    @cn.meteor.beyondclouds.modules.project.api.PutMapping("/project/{projectId}")
    public cn.meteor.beyondclouds.core.api.Response updateProject(@cn.meteor.beyondclouds.modules.project.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.project.form.ProjectForm projectForm, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.Integer projectId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        // 将项目表单转换为项目对象
        cn.meteor.beyondclouds.modules.project.entity.Project project = new cn.meteor.beyondclouds.modules.project.entity.Project();
        org.springframework.beans.BeanUtils.copyProperties(projectForm, project);
        project.setProjectId(projectId);
        // 设置用户ID
        project.setUserId(((java.lang.String) (subject.getId())));
        // 更新项目
        try {
            projectService.updateProject(project, projectForm.getContent(), projectForm.getContentHtml());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 删除项目
     *
     * @param projectId
     * @param subject
     * @return  */
    @io.swagger.annotations.ApiOperation("删除项目")
    @cn.meteor.beyondclouds.modules.project.api.DeleteMapping("/project/{projectId}")
    public cn.meteor.beyondclouds.core.api.Response deleteProject(@cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.String projectId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 删除
        try {
            projectService.deleteProject(projectId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 项目详情
     *
     * @param projectId
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("项目详情")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/project/{projectId}")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO> getProject(@cn.meteor.beyondclouds.modules.project.api.PathVariable("projectId")
    java.lang.String projectId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(paramName = "projectId", type = cn.meteor.beyondclouds.core.flow.ParamType.PROJECT)
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        boolean updateViewNum = false;
        if (cn.meteor.beyondclouds.util.AccessInfoUtils.hasFieldInfo(accessInfo)) {
            if (accessInfo.getFieldVisitCount() == 0) {
                updateViewNum = true;
            }
        }
        try {
            // 获取项目详情并返回
            cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO projectDetail = projectService.getProjectDetail(projectId, subject, updateViewNum);
            return cn.meteor.beyondclouds.core.api.Response.success(projectDetail);
        } catch (cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    /**
     * 项目列表
     *
     * @param pageForm
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("项目列表")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/projects")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project>> getProjects(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.project.api.RequestParam(value = "categoryId", required = false)
    java.lang.Integer categoryId) {
        // 获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> projectPage = projectService.getProjectPage(pageForm.getPage(), pageForm.getSize(), categoryId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project> projectPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(projectPage);
        return cn.meteor.beyondclouds.core.api.Response.success(projectPageDTO);
    }

    /**
     * 项目列表
     *
     * @param pageForm
     * @return  */
    @io.swagger.annotations.ApiOperation("我的项目列表")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/my/projects")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project>> getMyProjects(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 根据用户获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> projectPage = projectService.getProjectPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project> projectPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(projectPage);
        return cn.meteor.beyondclouds.core.api.Response.success(projectPageDTO);
    }

    /**
     * 项目列表
     *
     * @param pageForm
     * @return  */
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("他人项目列表")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/user/{userId}/projects")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project>> getOthersProjects(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.project.api.PathVariable("userId")
    java.lang.String userId) {
        // 根据用户获取列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> projectPage = projectService.getProjectPage(pageForm.getPage(), pageForm.getSize(), userId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project> projectPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(projectPage);
        return cn.meteor.beyondclouds.core.api.Response.success(projectPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门项目")
    @cn.meteor.beyondclouds.modules.project.api.GetMapping("/project/hots")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project>> getHotProjects(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm) {
        // 获取热门项目列表并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> projectPage = projectService.getHotProjectPage(pageForm.getPage(), pageForm.getSize());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.project.entity.Project> projectPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(projectPage);
        return cn.meteor.beyondclouds.core.api.Response.success(projectPageDTO);
    }
}