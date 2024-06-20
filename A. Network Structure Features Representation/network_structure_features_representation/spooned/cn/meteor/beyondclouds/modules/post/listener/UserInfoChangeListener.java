package cn.meteor.beyondclouds.modules.post.listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 用户信息更新监听器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component("postUserInfoChangeListener")
public class UserInfoChangeListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    private cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserInfoChangeListener(cn.meteor.beyondclouds.modules.post.service.IPostService postService, cn.meteor.beyondclouds.modules.post.service.IPostCommentService postCommentService) {
        this.postService = postService;
        this.postCommentService = postCommentService;
    }

    @java.lang.Override
    public void onUserAvatarUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        java.lang.String userId = java.lang.String.valueOf(dataItemChangeMessage.getItemId());
        postService.updatePostUserAvatar(userId);
        postCommentService.updatePostUserAvatar(userId);
        log.debug("post-用户头像更新：{}", dataItemChangeMessage);
    }

    @java.lang.Override
    public void onUserNickUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        java.lang.String userId = java.lang.String.valueOf(dataItemChangeMessage.getItemId());
        postService.updatePostUserNick(userId);
        postCommentService.updatePostUserNick(userId);
        log.debug("post-用户昵称更新：{}", dataItemChangeMessage);
    }
}