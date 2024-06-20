package cn.meteor.beyondclouds.modules.log.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum LogType {

    /**
     * 用户操作
     */
    USER_ACTION(1),
    /**
     * 数据改动
     */
    DATA_ITEM_CHANGE(2);
    private java.lang.Integer type;

    LogType(java.lang.Integer type) {
        this.type = type;
    }
}