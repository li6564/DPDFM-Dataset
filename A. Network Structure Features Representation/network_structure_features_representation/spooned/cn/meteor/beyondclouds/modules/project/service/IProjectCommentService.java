package cn.meteor.beyondclouds.modules.project.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 项目评论表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IProjectCommentService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> {
    /**
     * 发表评论
     *
     * @param projectId
     * @param parentId
     * @param comment
     * @param userId
     */
    void publishComment(java.lang.Integer projectId, java.lang.Integer parentId, java.lang.String comment, java.lang.String userId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException;

    /**
     * 删除评论
     *
     * @param commentId
     * @param id
     */
    void deleteComment(java.lang.Integer commentId, java.lang.String id) throws cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException;

    /**
     * 获取评论分页
     *
     * @param page
     * @param size
     * @param projectId
     * @param parentId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.ProjectComment> getCommentPage(java.lang.Integer page, java.lang.Integer size, java.lang.Integer projectId, java.lang.Integer parentId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectCommentServiceException;

    /**
     * 当用户更新头像时,更新项目评论中用户头像
     *
     * @param userId
     */
    void updateProjectCommentUserAvatar(java.lang.String userId);

    /**
     * 当用户更新昵称时,更新项目评论中用户昵称
     *
     * @param userId
     */
    void updateProjectCommentUserNick(java.lang.String userId);
}