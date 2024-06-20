package cn.meteor.beyondclouds.modules.message.dto;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class MessageDTO extends cn.meteor.beyondclouds.modules.message.entity.Message {
    /**
     * 未读数量
     */
    private java.lang.Integer unReadCount;

    /**
     * 总数量
     */
    private java.lang.Integer totalCount;
}