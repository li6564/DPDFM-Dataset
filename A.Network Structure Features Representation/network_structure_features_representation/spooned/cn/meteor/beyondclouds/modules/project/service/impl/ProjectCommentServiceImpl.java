package cn.meteor.beyondclouds.modules.project.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
/**
 * <p>
 * 项目评论表 服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
@org.springframework.stereotype.Service
public class ProjectCommentServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.project.mapper.ProjectCommentMapper, cn.meteor.beyondclouds.modules.project.entity.ProjectComment> implements cn.meteor.beyondclouds.modules.project.service.IProjectCommentService {
    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectService(cn.meteor.beyondclouds.modules.project.service.IProjectService projectService) {
        this.projectService = projectService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectPraiseService(cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService projectPraiseService) {
        this.projectPraiseService = projectPraiseService;
    }

    @java.lang.Override
    public void publishComment(java.lang.Integer projectId, java.lang.Integer parentId, java.lang.String comment, java.lang.String userId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException {
        org.springframework.util.Assert.notNull(projectId, "projectId must not be null");
        org.springframework.util.Assert.hasText(comment, "comment must not be empty");
        // 1. 查询项目是否存在
        cn.meteor.beyondclouds.modules.project.entity.Project project = projectService.getById(projectId);
        if (null == project) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
        }
        // 2.如果有parentId，查询父级评论在该项目中是否存在
        cn.meteor.beyondclouds.modules.project.entity.ProjectComment parentComment = null;
        if (null != parentId) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            projectCommentQueryWrapper.eq("project_id", projectId).eq("comment_id", parentId);
            parentComment = getOne(projectCommentQueryWrapper);
            if (null == parentComment) {
                throw new cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectCommentErrorCode.PARENT_COMMENT_NOT_FOUND);
            }
        }
        // 3.保存评论
        // 获取用户信息
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        cn.meteor.beyondclouds.modules.project.entity.ProjectComment projectComment = new cn.meteor.beyondclouds.modules.project.entity.ProjectComment();
        projectComment.setProjectId(projectId);
        projectComment.setUserId(userId);
        projectComment.setUserNick(user.getNickName());
        projectComment.setUserAvatar(user.getUserAvatar());
        projectComment.setParentId(parentId);
        projectComment.setComment(comment);
        save(projectComment);
        // 更新评论次数
        if (null == project.getCommentNumber()) {
            project.setCommentNumber(1);
        } else {
            project.setCommentNumber(project.getCommentNumber() + 1);
        }
        projectService.updateById(project);
        // 4.更新评论的深度和路径信息
        if (null == parentComment) {
            // 一级评论
            projectComment.setDepth(0);
            projectComment.setThread("/" + projectComment.getCommentId());
        } else {
            // 子级评论
            projectComment.setDepth(parentComment.getDepth() + 1);
            projectComment.setThread((parentComment.getThread() + "/") + projectComment.getCommentId());
        }
        updateById(projectComment);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT_COMMENT, projectComment.getCommentId()));
    }

    @java.lang.Override
    public void deleteComment(java.lang.Integer commentId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException {
        org.springframework.util.Assert.notNull(commentId, "commentId must not be null");
        org.springframework.util.Assert.notNull(userId, "userId must not be null");
        // 1.查找评论是否存在
        cn.meteor.beyondclouds.modules.project.entity.ProjectComment projectComment = getById(commentId);
        if (null == projectComment) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectCommentErrorCode.COMMENT_NOT_FOUND);
        }
        /**
         * 2.查看是否有权限删除评论
         * 自己发布的项目或者自己发布的评论才可以删除
         */
        if (!projectComment.getUserId().equals(userId)) {
            // 不是自己发布的评论， 再看看是不是自己项目下的评论
            cn.meteor.beyondclouds.modules.project.entity.Project project = projectService.getById(projectComment.getProjectId());
            if (!project.getUserId().equals(userId)) {
                // 既不是自己发布的评论， 又不是自己项目下的评论，没有权限删除评论
                throw new cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectCommentErrorCode.NO_DELETE_PRIVILEGES);
            }
        }
        // 3.删除评论及子评论以及赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectCommentQueryWrapper.like("thread", projectComment.getThread());
        java.util.List<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentList = list(projectCommentQueryWrapper);
        java.util.List<java.lang.Integer> commentIds = projectCommentList.stream().map(cn.meteor.beyondclouds.modules.project.entity.ProjectComment::getCommentId).collect(java.util.stream.Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(commentIds)) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> projectPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            projectPraiseQueryWrapper.in("target_id", commentIds);
            projectPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_COMMENT_PRAISE.getPraiseType());
            projectPraiseService.remove(projectPraiseQueryWrapper);
            remove(projectCommentQueryWrapper);
        }
    }

    @java.lang.Override
    public com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> getCommentPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer projectId, java.lang.Integer parentId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException {
        org.springframework.util.Assert.notNull(projectId, "projectId must not be null");
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize);
        // 1.如果parentId为null，则只获取一级评论
        if (null == parentId) {
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            projectCommentQueryWrapper.eq("project_id", projectId);
            // 只获取一级评论，也就是depth为0
            projectCommentQueryWrapper.eq("depth", 0);
            projectCommentQueryWrapper.orderByDesc("create_time");
            return page(page, projectCommentQueryWrapper);
        }
        // 1.如果parentId不为null，则获取其子评论
        // 判断父评论是否存在，不存在则抛出异常
        cn.meteor.beyondclouds.modules.project.entity.ProjectComment parentComment = getById(parentId);
        if (null == parentComment) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectCommentErrorCode.COMMENT_NOT_FOUND);
        }
        // 查询子评论
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        projectCommentQueryWrapper.eq("parent_id", parentId);
        return page(page, projectCommentQueryWrapper);
    }

    @java.lang.Override
    public void updateProjectCommentUserAvatar(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userAvatar = user.getUserAvatar();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            projectCommentUpdateWrapper.eq("user_id", userId);
            projectCommentUpdateWrapper.set("user_avatar", userAvatar);
            update(projectCommentUpdateWrapper);
        }
    }

    @java.lang.Override
    public void updateProjectCommentUserNick(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userId);
        if (null != user) {
            java.lang.String userNick = user.getNickName();
            com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> projectCommentUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
            projectCommentUpdateWrapper.eq("user_id", userId);
            projectCommentUpdateWrapper.set("user_nick", userNick);
            update(projectCommentUpdateWrapper);
        }
    }
}