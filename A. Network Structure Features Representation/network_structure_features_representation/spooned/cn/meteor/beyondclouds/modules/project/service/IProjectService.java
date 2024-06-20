package cn.meteor.beyondclouds.modules.project.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-01-30
 */
public interface IProjectService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.project.entity.Project> {
    /**
     * 发布项目
     *
     * @param project
     * @param content
     * @param contentHtml
     */
    void publishProject(cn.meteor.beyondclouds.modules.project.entity.Project project, java.lang.String content, java.lang.String contentHtml) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 删除项目
     *
     * @param projectId
     * @param userId
     */
    void deleteProject(java.lang.String projectId, java.lang.String userId) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 获取项目
     *
     * @param projectId
     * @param subject
     * @param updateViewNum
     * @return  */
    cn.meteor.beyondclouds.modules.project.dto.ProjectDetailDTO getProjectDetail(java.lang.String projectId, cn.meteor.beyondclouds.core.authentication.Subject subject, boolean updateViewNum) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 获取项目列表
     *
     * @return  * @param pageNumber
     * @param pageSize
     */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> getProjectPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.Integer categoryId);

    /**
     * 更新项目
     *
     * @param project
     * @param content
     * @param contentHtml
     */
    void updateProject(cn.meteor.beyondclouds.modules.project.entity.Project project, java.lang.String content, java.lang.String contentHtml) throws cn.meteor.beyondclouds.modules.project.exception.ProjectServiceException;

    /**
     * 根据用户ID获取项目列表
     *
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> getProjectPage(java.lang.Integer pageNumber, java.lang.Integer pageSize, java.lang.String userId);

    /**
     * 获取热门项目列表
     *
     * @param pageNumber
     * @param pageSize
     * @return  */
    com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.project.entity.Project> getHotProjectPage(java.lang.Integer pageNumber, java.lang.Integer pageSize);

    /**
     * 当用户更新昵称时,更新项目中用户昵称
     *
     * @param userId
     */
    void updateProjectUserNick(java.lang.String userId);
}