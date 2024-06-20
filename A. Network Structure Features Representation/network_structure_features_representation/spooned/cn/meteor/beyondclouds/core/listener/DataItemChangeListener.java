package cn.meteor.beyondclouds.core.listener;
import lombok.extern.slf4j.Slf4j;
/**
 * 数据更新监听器
 * 监听本系统所有的数据更新 操作：更新操作包括
 *
 * @author meteor
 */
public interface DataItemChangeListener {
    /**
     * 有新数据添加到数据库
     *
     * @param dataItemChangeMessage
     */
    default void onDataItemAdd(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
    }

    /**
     * 有数据从数据库删除
     *
     * @param dataItemChangeMessage
     */
    default void onDataItemDelete(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
    }

    /**
     * 数据库里面的数据更新
     *
     * @param dataItemChangeMessage
     */
    default void onDataItemUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
    }

    /**
     * 用户昵称更新
     *
     * @param dataItemChangeMessage
     */
    default void onUserNickUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
    }

    /**
     * 用户头像更新
     *
     * @param dataItemChangeMessage
     */
    default void onUserAvatarUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
    }
}