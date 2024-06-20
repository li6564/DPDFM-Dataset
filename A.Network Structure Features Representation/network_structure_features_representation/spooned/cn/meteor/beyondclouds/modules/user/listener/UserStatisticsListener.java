package cn.meteor.beyondclouds.modules.user.listener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component
public class UserStatisticsListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService;

    private cn.meteor.beyondclouds.modules.post.service.IPostService postService;

    private cn.meteor.beyondclouds.modules.project.service.IProjectService projectService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionService(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.questionService = questionService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public UserStatisticsListener(cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService, cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService, cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService, cn.meteor.beyondclouds.modules.project.service.IProjectService projectService, cn.meteor.beyondclouds.modules.post.service.IPostService postService, cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService) {
        this.userStatisticsService = userStatisticsService;
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setQuestionReplyService(cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService) {
        this.questionReplyService = questionReplyService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setBlogService(cn.meteor.beyondclouds.modules.blog.service.IBlogService blogService) {
        this.blogService = blogService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPostService(cn.meteor.beyondclouds.modules.post.service.IPostService postService) {
        this.postService = postService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setProjectService(cn.meteor.beyondclouds.modules.project.service.IProjectService projectService) {
        this.projectService = projectService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @java.lang.Override
    public void onDataItemAdd(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        onDataChange(dataItemChangeMessage);
    }

    @java.lang.Override
    public void onDataItemDelete(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        onDataChange(dataItemChangeMessage);
    }

    /**
     * 博客浏览量改变或回答被采纳
     *
     * @param dataItemChangeMessage
     */
    @java.lang.Override
    public void onDataItemUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        if (dataItemChangeMessage.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_VIEW_NUM)) {
            onDataChange(dataItemChangeMessage);
        }
        if (dataItemChangeMessage.getItemType().equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY_ACCEPT)) {
            onDataChange(dataItemChangeMessage);
        }
    }

    /**
     * 汇总ADD和delete消息
     *
     * @param dataItemChangeMessage
     */
    private void onDataChange(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        cn.meteor.beyondclouds.core.queue.message.DataItemType itemType = dataItemChangeMessage.getItemType();
        java.io.Serializable itemId = dataItemChangeMessage.getItemId();
        java.lang.String operatorId = dataItemChangeMessage.getOperatorId();
        // 关注用户
        if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_FOLLOW)) {
            cn.meteor.beyondclouds.modules.user.entity.UserFollow userFollow = userFollowService.getById(itemId);
            java.lang.String followedUserId = userFollow.getFollowedId();
            java.lang.String followerUserId = userFollow.getFollowerId();
            // 更新被关注用户的粉丝量
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> followedUserFansNumQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            followedUserFansNumQueryWrapper.eq("followed_id", userFollow.getFollowedId());
            followedUserFansNumQueryWrapper.eq("follow_status", 0);
            long followedUserFansNum = userFollowService.count(followedUserFansNumQueryWrapper);
            updateUserStatisticValue(userFollow.getFollowedId(), "fans_num", followedUserFansNum);
            // 发送被关注用户统计信息修改的消息
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_STATISTICS, followedUserId));
            // 更新关注者的关注量
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserFollow> followerUserFollowedNumQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            followerUserFollowedNumQueryWrapper.eq("follower_id", userFollow.getFollowerId());
            followerUserFollowedNumQueryWrapper.eq("follow_status", 0);
            long followerUserFollowedNum = userFollowService.count(followerUserFollowedNumQueryWrapper);
            updateUserStatisticValue(userFollow.getFollowerId(), "followed_num", followerUserFollowedNum);
            // 发送关注者统计信息修改的消息
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_STATISTICS, followerUserId));
        } else {
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG)) {
                // 更新博客发布量
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.blog.entity.Blog> blogQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                blogQueryWrapper.eq("user_id", operatorId);
                blogQueryWrapper.eq("status", 0);
                long blogCount = blogService.count(blogQueryWrapper);
                log.debug("blog count is : {}", blogCount);
                updateUserStatisticValue(operatorId, "blog_num", blogCount);
            }
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG_VIEW_NUM)) {
                cn.meteor.beyondclouds.modules.blog.entity.Blog blog = blogService.getById(itemId);
                java.lang.String userId = blog.getUserId();
                // 更新博客浏览量
                updateUserStatisticValue(userId, "blog_view_num", blogService.allBlogViewCount(userId));
            }
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT)) {
                // 更新项目发布量
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.project.entity.Project> projectQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                projectQueryWrapper.eq("user_id", operatorId);
                projectQueryWrapper.eq("status", 1);
                long projectCount = projectService.count(projectQueryWrapper);
                log.debug("project count is : {}", projectCount);
                updateUserStatisticValue(operatorId, "project_num", projectCount);
            }
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.POST)) {
                // 更新动态发布量
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.post.entity.Post> postQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                postQueryWrapper.eq("user_id", operatorId);
                postQueryWrapper.eq("status", 0);
                long postCount = postService.count(postQueryWrapper);
                log.debug("post count is : {}", postCount);
                updateUserStatisticValue(operatorId, "post_num", postCount);
            }
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION)) {
                // 更新问答发布量
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.Question> questionQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                questionQueryWrapper.eq("user_id", operatorId);
                questionQueryWrapper.eq("status", 0);
                updateUserStatisticValue(operatorId, "question_num", questionService.count(questionQueryWrapper));
            }
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY)) {
                // 更新问题回答量
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                questionReplyQueryWrapper.eq("user_id", operatorId);
                questionReplyQueryWrapper.in("reply_status", java.util.List.of(0, 1));
                updateUserStatisticValue(operatorId, "question_reply_num", questionReplyService.count(questionReplyQueryWrapper));
            }
            if (itemType.equals(cn.meteor.beyondclouds.core.queue.message.DataItemType.QUESTION_REPLY_ACCEPT)) {
                // 更新答案被采纳量
                cn.meteor.beyondclouds.modules.question.entity.QuestionReply questionReply = questionReplyService.getById(itemId);
                java.lang.String userId = questionReply.getUserId();
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.question.entity.QuestionReply> questionReplyQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                questionReplyQueryWrapper.eq("user_id", userId);
                questionReplyQueryWrapper.eq("reply_status", 1);
                updateUserStatisticValue(userId, "reply_accepted_num", questionReplyService.count(questionReplyQueryWrapper));
            }
            // 修改完用户统计信息后发送用户统计信息修改的通知
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER_STATISTICS, operatorId));
        }
    }

    /**
     * 更新用户统计信息
     *
     * @param userId
     * @param column
     * @param value
     */
    private <T> void updateUserStatisticValue(java.lang.String userId, java.lang.String column, T value) {
        updateUserStatistics(userId, (column + " = ") + value);
    }

    /**
     * 更新用户的统计信息
     *
     * @param userId
     * @param setSql
     */
    private void updateUserStatistics(java.lang.String userId, java.lang.String setSql) {
        userStatisticsService.update(makeUserStatisticsUpdateWrapper(userId, setSql));
    }

    /**
     * 创建用来更新用户统计信息的queryWrapper
     *
     * @param userId
     * @param setSql
     * @return  */
    private com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> makeUserStatisticsUpdateWrapper(java.lang.String userId, java.lang.String setSql) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> userStatisticsUpdateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<>();
        userStatisticsUpdateWrapper.eq("user_id", userId);
        userStatisticsUpdateWrapper.setSql(setSql);
        return userStatisticsUpdateWrapper;
    }
}