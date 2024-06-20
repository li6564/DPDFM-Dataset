package cn.meteor.beyondclouds.modules.message.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum MessageStatus {

    /**
     * 未读
     */
    UN_READ(0),
    /**
     * 已读
     */
    READ(1);
    private java.lang.Integer status;

    MessageStatus(java.lang.Integer status) {
        this.status = status;
    }
}