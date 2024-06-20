package cn.meteor.beyondclouds.modules.search.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author meteor
 */
@lombok.Data
@lombok.NoArgsConstructor
public class SearchItemId {
    private cn.meteor.beyondclouds.core.queue.message.DataItemType itemType;

    private java.lang.String itemId;

    public SearchItemId(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) {
        this.itemType = itemType;
        this.itemId = itemId;
    }

    public static cn.meteor.beyondclouds.modules.search.entity.SearchItemId of(cn.meteor.beyondclouds.core.queue.message.DataItemType dataItemType, java.lang.String itemId) {
        return new cn.meteor.beyondclouds.modules.search.entity.SearchItemId(dataItemType, itemId);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (itemType.name() + ":") + itemId;
    }
}