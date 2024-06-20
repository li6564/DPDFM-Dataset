package cn.meteor.beyondclouds.modules.question.listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * 用户信息更新监听器
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Component("questionUserInfoChangeListener")
public class UserInfoChangeListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService;

    private cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService;

    public UserInfoChangeListener(cn.meteor.beyondclouds.modules.question.service.IQuestionService questionService, cn.meteor.beyondclouds.modules.question.service.IQuestionReplyService questionReplyService, cn.meteor.beyondclouds.modules.question.service.IQuestionReplyCommentService questionReplyCommentService) {
        this.questionService = questionService;
        this.questionReplyService = questionReplyService;
        this.questionReplyCommentService = questionReplyCommentService;
    }

    // @Autowired
    // public void setQuestionService(IQuestionService questionService) {
    // this.questionService = questionService;
    // }
    @java.lang.Override
    public void onUserAvatarUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        java.lang.String userId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        questionReplyService.updateQuestionReplyUserAvatar(userId);
        questionReplyCommentService.updateQuestionReplyCommentUserAvatar(userId);
        log.debug("question-用户头像更新：{}", dataItemChangeMessage);
    }

    @java.lang.Override
    public void onUserNickUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        java.lang.String userId = ((java.lang.String) (dataItemChangeMessage.getItemId()));
        questionService.updateQuestionUserNick(userId);
        questionReplyService.updateQuestionReplyUserNick(userId);
        questionReplyCommentService.updateQuestionReplyCommentUserNick(userId);
        log.debug("question-用户昵称更新：{}", dataItemChangeMessage);
    }
}