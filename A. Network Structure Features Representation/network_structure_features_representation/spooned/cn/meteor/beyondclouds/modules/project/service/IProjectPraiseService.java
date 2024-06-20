package cn.meteor.beyondclouds.modules.project.service;
import cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-20
 */
public interface IProjectPraiseService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.project.entity.ProjectPraise> {
    /**
     * 点赞
     *
     * @param currentUserId
     * @param projectId
     * @throws ProjectServiceException
     */
    void praiseProject(java.lang.String currentUserId, java.lang.String projectId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 取消点赞
     *
     * @param currentUserId
     * @param projectId
     */
    void deleteProjectPraise(java.lang.String currentUserId, java.lang.String projectId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 项目评论点赞
     *
     * @param currentUserId
     * @param commentId
     */
    void praiseProjectComment(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 取消项目评论点赞
     *
     * @param currentUserId
     * @param commentId
     */
    void deleteProjectCommentPraise(java.lang.String currentUserId, java.lang.String commentId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 项目点赞列表
     *
     * @param page
     * @param size
     * @param projectId
     * @return  */
    cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.PraiseUserDTO> getProjectPraises(java.lang.Integer page, java.lang.Integer size, java.lang.String projectId);
}