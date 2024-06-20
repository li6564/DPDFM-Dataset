package cn.meteor.beyondclouds.core.queue.message;
/**
 * 数据更新操作
 *
 * @author meteor
 */
public enum DataItemChangeType {

    /**
     * 新增条目
     */
    ADD,
    /**
     * 删除条目
     */
    DELETE,
    /**
     * 更新条目
     */
    UPDATE,
    /**
     * 用户头像更新
     */
    USER_AVATAR_UPDATE,
    /**
     * 用户昵称更新
     */
    USER_NICK_UPDATE;}