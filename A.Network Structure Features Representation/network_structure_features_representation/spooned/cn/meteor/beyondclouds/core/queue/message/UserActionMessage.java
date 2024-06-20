package cn.meteor.beyondclouds.core.queue.message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 *
 * @author meteor
 */
@lombok.Data
@lombok.ToString
@lombok.NoArgsConstructor
public class UserActionMessage {
    public UserActionMessage(cn.meteor.beyondclouds.core.authentication.Subject subject, cn.meteor.beyondclouds.core.queue.message.UserAction action) {
        this.subject = subject;
        this.userId = ((java.lang.String) (subject.getId()));
        this.action = action;
    }

    public UserActionMessage(cn.meteor.beyondclouds.core.authentication.Subject subject, cn.meteor.beyondclouds.core.queue.message.UserAction action, java.lang.String accountType, java.lang.String account) {
        this.subject = subject;
        this.userId = ((java.lang.String) (subject.getId()));
        this.action = action;
        this.accountType = accountType;
        this.account = account;
    }

    private cn.meteor.beyondclouds.core.authentication.Subject subject;

    /**
     * 用户ID
     */
    private java.lang.String userId;

    /**
     * 操作
     */
    private cn.meteor.beyondclouds.core.queue.message.UserAction action;

    private java.lang.String accountType;

    private java.lang.String account;

    /**
     * 用户登录消息
     *
     * @param subject
     * @return  */
    public static cn.meteor.beyondclouds.core.queue.message.UserActionMessage loginMessage(cn.meteor.beyondclouds.core.authentication.Subject subject, cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal) {
        java.lang.String accountType = (userAuthLocal.getAccountType() == 1) ? "MOBILE" : "EMAIL";
        return new cn.meteor.beyondclouds.core.queue.message.UserActionMessage(subject, cn.meteor.beyondclouds.core.queue.message.UserAction.LOGIN, accountType, userAuthLocal.getAccount());
    }

    /**
     * 用户登录失败消息
     *
     * @param subject
     * @return  */
    public static cn.meteor.beyondclouds.core.queue.message.UserActionMessage loginFailureMessage(cn.meteor.beyondclouds.core.authentication.Subject subject, java.lang.String loginType, java.lang.String secret) {
        return new cn.meteor.beyondclouds.core.queue.message.UserActionMessage(subject, cn.meteor.beyondclouds.core.queue.message.UserAction.LOGIN_FAILURE, loginType, secret);
    }

    /**
     * 用户注销消息
     *
     * @param subject
     * @return  */
    public static cn.meteor.beyondclouds.core.queue.message.UserActionMessage logoutMessage(cn.meteor.beyondclouds.core.authentication.Subject subject) {
        return new cn.meteor.beyondclouds.core.queue.message.UserActionMessage(subject, cn.meteor.beyondclouds.core.queue.message.UserAction.LOGOUT);
    }
}