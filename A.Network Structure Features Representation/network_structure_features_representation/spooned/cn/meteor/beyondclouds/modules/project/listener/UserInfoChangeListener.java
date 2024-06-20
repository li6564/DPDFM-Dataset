package cn.meteor.beyondclouds.modules.project.listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 用户信息更新监听器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component("projectUserInfoChangeListener")
public class UserInfoChangeListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectService(cn.meteor.beyondclouds.modules.project.service.IProjectService projectService) {
        this.projectService = projectService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectCommentService(cn.meteor.beyondclouds.modules.project.service.IProjectCommentService projectCommentService) {
        this.projectCommentService = projectCommentService;
    }

    @java.lang.Override
    public void onUserAvatarUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        java.lang.String userId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        projectCommentService.updateProjectCommentUserAvatar(userId);
        log.debug("project-用户头像更新：{}", dataItemChangeMessage);
    }

    @java.lang.Override
    public void onUserNickUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        java.lang.String userId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        projectService.updateProjectUserNick(userId);
        projectCommentService.updateProjectCommentUserNick(userId);
        log.debug("project-用户昵称更新：{}", dataItemChangeMessage);
    }
}