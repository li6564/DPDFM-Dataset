package cn.meteor.beyondclouds.modules.blog.listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 用户信息更新监听器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component("blogUserInfoChangeListener")
public class UserInfoChangeListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserInfoChangeListener(cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService, cn.meteor.beyondclouds.modules.blog.service.IBlogCommentService blogCommentService) {
        this.blogService = blogService;
        this.blogCommentService = blogCommentService;
    }

    @java.lang.Override
    public void onUserAvatarUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        log.debug("blog-用户头像更新：{}", dataItemChangeMessage);
        java.lang.String userId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        blogCommentService.updateBlogCommentUserAvatar(userId);
    }

    @java.lang.Override
    public void onUserNickUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        log.debug("bolg-用户昵称更新：{}", dataItemChangeMessage);
        java.lang.String userId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        blogService.updateBlogUserNick(userId);
        blogCommentService.updateBlogCommentUserNick(userId);
    }
}