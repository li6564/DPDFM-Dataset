package cn.meteor.beyondclouds.core.queue.message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 条目更新消息
 *
 * @author meteor
 */
@lombok.Data
@lombok.ToString
@lombok.NoArgsConstructor
public class DataItemChangeMessage {
    public DataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType changeType, cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.io.Serializable itemId) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.changeType = changeType;
        this.subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        if (subject.isAuthenticated()) {
            this.operatorId = ((java.lang.String) (subject.getId()));
        } else {
            this.operatorId = subject.getIpAddress();
        }
    }

    public static cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage addMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.io.Serializable itemId) {
        return new cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType.ADD, itemType, itemId);
    }

    public static cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage deleteMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.io.Serializable itemId) {
        return new cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType.DELETE, itemType, itemId);
    }

    public static cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.io.Serializable itemId) {
        return new cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType.UPDATE, itemType, itemId);
    }

    public static cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage userAvatarUpdateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.io.Serializable itemId) {
        return new cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType.USER_AVATAR_UPDATE, itemType, itemId);
    }

    public static cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage userNickUpdateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.io.Serializable itemId) {
        return new cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType.USER_NICK_UPDATE, itemType, itemId);
    }

    /**
     * 条目ID
     */
    private java.io.Serializable itemId;

    /**
     * 操作者ID
     */
    private java.lang.String operatorId;

    /**
     * 操作者
     */
    private cn.meteor.beyondclouds.core.authentication.Subject subject;

    /**
     * 条目类型
     */
    private cn.meteor.beyondclouds.core.queue.message.DataItemType itemType;

    /**
     * 条目改变类型
     */
    private cn.meteor.beyondclouds.core.queue.message.DataItemChangeType changeType;
}