package cn.meteor.beyondclouds.modules.project.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class ProjectServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.project.mapper.ProjectMapper, cn.meteor.beyondclouds.modules.project.entity.Project> implements cn.meteor.beyondclouds.modules.project.service.IProjectService {
    private cn.meteor.beyondclouds.modules.project.service.IProjectExtService projectExtService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectCategoryService projectCategoryService;

    @org.springframework.beans.factory.annotation.Autowired
    public ProjectServiceImpl(cn.meteor.beyondclouds.modules.project.service.IProjectExtService projectExtService) {
        this.projectExtService = projectExtService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectCategoryService(cn.meteor.beyondclouds.modules.project.service.IProjectCategoryService projectCategoryService) {
        this.projectCategoryService = projectCategoryService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectPraiseService(cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService) {
        this.projectPraiseService = projectPraiseService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectCommentService(cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService) {
        this.projectCommentService = projectCommentService;
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void publishProject(cn.meteor.beyondclouds.modules.project.entity.Project project, java.lang.String content, java.lang.String contentHtml) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        org.springframework.util.Assert.notNull(project, "project must not be null");
        org.springframework.util.Assert.hasText(project.getUserId(), "userId must not be empty");
        // 1.检查项目类别是否存在
        java.lang.Integer categoryId = project.getCategoryId();
        cn.meteor.beyondclouds.modules.project.entity.ProjectCategory projectCategory = projectCategoryService.getById(categoryId);
        // 若分类不存在，则抛出异常
        if (null == projectCategory) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.INCORRECT_CATEGORY);
        }
        // 获取用户昵称
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(project.getUserId());
        project.setUserNick(user.getNickName());
        // 2.保存项目
        project.setCategory(projectCategory.getCategory());
        project.setStatus(0);
        save(project);
        // 3.保存项目详情
        cn.meteor.beyondclouds.modules.project.entity.ProjectExt projectExt = new cn.meteor.beyondclouds.modules.project.entity.ProjectExt();
        projectExt.setProjectId(project.getProjectId());
        projectExt.setContent(content);
        projectExt.setContentHtml(contentHtml);
        projectExtService.save(projectExt);
        // 4.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT, project.getProjectId()));
    }

    @java.lang.Override
    public void deleteProject(java.lang.String projectId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        org.springframework.util.Assert.notNull(projectId, "projectId must not be null");
        org.springframework.util.Assert.notNull(userId, "userId must not be null");
        // 1.判断自己有没有发过这个项目
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectQueryWrapper.eq("project_id", projectId).eq("user_id", userId);
        cn.meteor.beyondclouds.modules.project.entity.Project project = getOne(projectQueryWrapper);
        // 若找不到该用户的该项目，则抛出业务异常
        if (null == project) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() == (-1)) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_DISABLED);
        }
        // 2.删除项目的所有评论和对评论的赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectCommentQueryWrapper.eq("project_id", projectId);
        java.util.List<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectList = projectCommentService.list(projectCommentQueryWrapper);
        java.util.List<java.lang.Integer> commentIds = projectList.stream().map(cn.meteor.beyondclouds.modules.project.entity.ProjectComment::getCommentId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(commentIds)) {
            // 删点赞
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> projectPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            projectPraiseQueryWrapper.in("target_id", commentIds);
            projectPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_COMMENT_PRAISE.getPraiseType());
            projectPraiseService.remove(projectPraiseQueryWrapper);
            // 删评论
            projectCommentService.remove(projectCommentQueryWrapper);
        }
        // 删除项目详情
        projectExtService.removeById(projectId);
        // 删除项目的赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> projectPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectPraiseQueryWrapper.eq("target_id", projectId);
        projectPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_PRAISE.getPraiseType());
        projectPraiseService.remove(projectPraiseQueryWrapper);
        // 删除项目
        removeById(projectId);
        // 4.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.deleteMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT, project.getProjectId()));
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO getProjectDetail(java.lang.String projectId, cn.meteor.beyondclouds.core.authentication.Subject subject, boolean updateViewNum) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        org.springframework.util.Assert.notNull(projectId, "projectId must not be null");
        // 1.获取项目
        cn.meteor.beyondclouds.modules.project.entity.Project project = getById(projectId);
        // 若找不到该项目，则抛出业务异常
        if (null == project) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() == (-1)) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_DISABLED);
        }
        // 用户未认证或者不是查看自己的项目
        if ((!cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) || (!subject.getId().equals(project.getUserId()))) {
            // 项目还没审核通过
            if (project.getStatus() != 1) {
                throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
            }
        }
        // 获取项目浏览量加一并更新
        if (updateViewNum) {
            project.setViewNumber(project.getViewNumber() + 1);
            updateById(project);
        }
        // 2.获取项目详情
        cn.meteor.beyondclouds.modules.project.entity.ProjectExt projectExt = projectExtService.getById(projectId);
        // 3.装配并返回查询到的信息
        cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO projectDetail = new cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO();
        org.springframework.beans.BeanUtils.copyProperties(project, projectDetail);
        projectDetail.setContent(projectExt.getContent());
        projectDetail.setContentHtml(projectExt.getContentHtml());
        // 查看当前用户有没有关注作者
        java.lang.String authorId = project.getUserId();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            projectDetail.setFollowedAuthor(userFollowService.hasFollowedUser(authorId));
        } else {
            projectDetail.setFollowedAuthor(false);
        }
        // 查看当前用户有没有对该项目点赞
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> projectPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            projectPraiseQueryWrapper.eq("user_id", subject.getId());
            projectPraiseQueryWrapper.eq("target_id", project.getProjectId());
            projectPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_PRAISE.getPraiseType());
            cn.meteor.beyondclouds.modules.project.entity.ProjectPraise projectPraise = projectPraiseService.getOne(projectPraiseQueryWrapper);
            projectDetail.setPraised(null != projectPraise);
        } else {
            projectDetail.setPraised(false);
        }
        return projectDetail;
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> getProjectPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer categoryId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectQueryWrapper.eq("status", 1);
        projectQueryWrapper.orderByDesc("priority");
        projectQueryWrapper.orderByDesc("create_time");
        if (null != categoryId) {
            projectQueryWrapper.eq("category_id", categoryId);
        }
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        return page(page, projectQueryWrapper);
    }

    @java.lang.Override
    public void updateProject(cn.meteor.beyondclouds.modules.project.entity.Project project, java.lang.String content, java.lang.String contentHtml) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        org.springframework.util.Assert.notNull(project, "project must not be null");
        org.springframework.util.Assert.hasText(project.getUserId(), "userId must not be empty");
        org.springframework.util.Assert.notNull(project.getProjectId(), "projectId must not be null");
        // 1.判断自己有没有发过这个项目
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectQueryWrapper.eq("user_id", project.getUserId()).eq("project_id", project.getProjectId());
        cn.meteor.beyondclouds.modules.project.entity.Project projectInDb = getOne(projectQueryWrapper);
        // 若找不到该项目，则抛出业务异常
        if (null == projectInDb) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
        }
        if (projectInDb.getStatus() == (-1)) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_DISABLED);
        }
        // 检查项目类别是否存在
        java.lang.Integer categoryId = project.getCategoryId();
        cn.meteor.beyondclouds.modules.project.entity.ProjectCategory projectCategory = projectCategoryService.getById(categoryId);
        // 若分类不存在，则抛出异常
        if (null == projectCategory) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.INCORRECT_CATEGORY);
        }
        // 2.更新项目基本信息
        project.setCategory(projectCategory.getCategory());
        updateById(project);
        // 3.更新项目详情
        if (!org.springframework.util.StringUtils.isEmpty(content)) {
            cn.meteor.beyondclouds.modules.project.entity.ProjectExt projectExt = new cn.meteor.beyondclouds.modules.project.entity.ProjectExt();
            projectExt.setProjectId(project.getProjectId());
            projectExt.setContent(content);
            projectExt.setContentHtml(contentHtml);
            projectExtService.updateById(projectExt);
        }
        // 4.发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT, project.getProjectId()));
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> getProjectPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectQueryWrapper.eq("user_id", userId);
        if ((!cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) || (!cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId().equals(userId))) {
            projectQueryWrapper.eq("status", 1);
        }
        projectQueryWrapper.orderByDesc("create_time");
        return page(page, projectQueryWrapper);
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> getHotProjectPage(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectQueryWrapper.eq("status", 1);
        projectQueryWrapper.orderByDesc("view_number");
        return page(page, projectQueryWrapper);
    }

    @java.lang.Override
    public void updateProjectUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userNick = user.getNickName();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            projectUpdateWrapper.eq("user_id", userId);
            projectUpdateWrapper.set("user_nick", userNick);
            update(projectUpdateWrapper);
        }
    }
}