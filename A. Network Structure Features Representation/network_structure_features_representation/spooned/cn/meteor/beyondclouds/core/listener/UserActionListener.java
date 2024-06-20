package cn.meteor.beyondclouds.core.listener;
import lombok.extern.slf4j.Slf4j;
/**
 * 用户行为监听器
 * 监听本系统所有的数据更新 操作：更新操作包括
 *
 * @author meteor
 */
public interface UserActionListener {
    /**
     * 登录
     *
     * @param userActionMessage
     */
    default void onUserLogin(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
    }

    /**
     * 登录失败
     *
     * @param userActionMessage
     */
    default void onUserLoginFailure(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
    }

    /**
     * 注销
     *
     * @param userActionMessage
     */
    default void onUserLogout(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
    }

    /**
     * 注册
     *
     * @param userActionMessage
     */
    default void onUserRegister(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
    }
}