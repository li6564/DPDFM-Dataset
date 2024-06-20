package cn.meteor.beyondclouds.modules.project.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
@org.springframework.stereotype.Service
public class ProjectPraiseServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.project.mapper.ProjectPraiseMapper, cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> implements cn.meteor.beyondclouds.modules.project.service.IProjectPraiseService {
    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.project.mapper.ProjectPraiseMapper projectPraiseMapper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectService(cn.meteor.beyondclouds.modules.project.service.IProjectService projectService) {
        this.projectService = projectService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectCommentService(cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService) {
        this.projectCommentService = projectCommentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectPraiseMapper(cn.meteor.beyondclouds.modules.project.mapper.ProjectPraiseMapper projectPraiseMapper) {
        this.projectPraiseMapper = projectPraiseMapper;
    }

    @java.lang.Override
    public void praiseProject(java.lang.String currentUserId, java.lang.String projectId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        // 1.查询项目是否存在
        cn.meteor.beyondclouds.modules.project.entity.Project project = projectService.getById(projectId);
        if (null == project) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
        }
        // 2.查询是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> projectPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        projectPraiseQueryWrapper.eq("user_id", currentUserId);
        projectPraiseQueryWrapper.eq("target_id", projectId);
        projectPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.project.entity.ProjectPraise projectPraise = getOne(projectPraiseQueryWrapper);
        if (null != projectPraise) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseErrorCode.ALREADY_PRAISE);
        }
        // 保存点赞
        projectPraise = new cn.meteor.beyondclouds.modules.project.entity.ProjectPraise();
        projectPraise.setUserId(currentUserId);
        projectPraise.setTargetId(projectId);
        projectPraise.setTargetType(cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_PRAISE.getPraiseType());
        save(projectPraise);
        // 更新获赞数量
        project.setPraiseNum(project.getPraiseNum() + 1);
        projectService.updateById(project);
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT_PRAISE, projectPraise.getPraiseId()));
    }

    @java.lang.Override
    public void deleteProjectPraise(java.lang.String currentUserId, java.lang.String projectId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        cn.meteor.beyondclouds.modules.project.entity.Project project = projectService.getById(projectId);
        if (null == project) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectErrorCode.PROJECT_NOT_FOUND);
        }
        // 1.判断是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("user_id", currentUserId);
        queryWrapper.eq("target_id", projectId);
        queryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.project.entity.ProjectPraise projectPraise = getOne(queryWrapper);
        if (null == projectPraise) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseErrorCode.NON_PRAISE);
        }
        // 1.删除
        remove(queryWrapper);
        // 更新获赞数量
        project.setPraiseNum(project.getPraiseNum() - 1);
        projectService.updateById(project);
    }

    @java.lang.Override
    public void praiseProjectComment(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        // 1.查询评论是否存在
        cn.meteor.beyondclouds.modules.project.entity.ProjectComment projectComment = projectCommentService.getById(commentId);
        if (null == projectComment) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectCommentErrorCode.COMMENT_NOT_FOUND);
        }
        // 2.查询是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> projectPraiseQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        projectPraiseQueryWrapper.eq("user_id", currentUserId);
        projectPraiseQueryWrapper.eq("target_id", commentId);
        projectPraiseQueryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_COMMENT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.project.entity.ProjectPraise projectPraise = getOne(projectPraiseQueryWrapper);
        if (null != getOne(projectPraiseQueryWrapper)) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseErrorCode.ALREADY_PRAISE);
        }
        // 保存点赞
        projectPraise = new cn.meteor.beyondclouds.modules.project.entity.ProjectPraise();
        projectPraise.setUserId(currentUserId);
        projectPraise.setTargetId(commentId);
        projectPraise.setTargetType(cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_COMMENT_PRAISE.getPraiseType());
        save(projectPraise);
    }

    @java.lang.Override
    public void deleteProjectCommentPraise(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException {
        // 1.判断是否点过赞
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryWrapper.eq("user_id", currentUserId);
        queryWrapper.eq("target_id", commentId);
        queryWrapper.eq("target_type", cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_COMMENT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.modules.project.entity.ProjectPraise projectPraise = getOne(queryWrapper);
        if (null == projectPraise) {
            throw new cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException(cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseErrorCode.NON_PRAISE);
        }
        // 1.删除
        remove(queryWrapper);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getProjectPraises(java.lang.Integer page, java.lang.Integer size, java.lang.String projectId) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> praiseUserDTOPage = projectPraiseMapper.selectPraisePage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page(page, size), projectId, cn.meteor.beyondclouds.modules.project.enums.ProjectPraiseType.PROJECT_PRAISE.getPraiseType());
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(praiseUserDTOPage, pageDTO);
        pageDTO.setDataList(praiseUserDTOPage.getRecords());
        return pageDTO;
    }
}