package cn.meteor.beyondclouds.modules.search.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
/**
 *
 * @author meteor
 */
@lombok.ToString
@lombok.Data
@lombok.NoArgsConstructor
@org.springframework.data.elasticsearch.annotations.Document(indexName = "search_items")
public class SearchItem<T> {
    /**
     * 快捷构造函数
     * 设置数据条目的主键，itemType和itemId
     *
     * @param itemType
     * @param itemId
     */
    public SearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType itemType, java.lang.String itemId) {
        this.id = cn.meteor.beyondclouds.modules.search.entity.SearchItemId.of(itemType, itemId);
        this.itemType = itemType;
        this.itemId = itemId;
    }

    @org.springframework.data.annotation.Id
    private cn.meteor.beyondclouds.modules.search.entity.SearchItemId id;

    /**
     * 数据的主键
     */
    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.lang.String itemId;

    /**
     * 数据类型
     */
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private cn.meteor.beyondclouds.core.queue.message.DataItemType itemType;

    /**
     * 数据标题
     */
    @org.springframework.data.elasticsearch.annotations.Field(analyzer = "ik_max_word", type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.lang.String title;

    /**
     * 作者
     */
    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.lang.String author;

    /**
     * 数据封面图
     */
    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.lang.String cover;

    /**
     * 数据摘要
     */
    @org.springframework.data.elasticsearch.annotations.Field(analyzer = "ik_max_word", type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.lang.String description;

    /**
     * 数据内容
     */
    @org.springframework.data.elasticsearch.annotations.Field(analyzer = "ik_max_word", type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.lang.String content;

    /**
     * 数据创建时间
     */
    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private java.util.Date createTime;

    /**
     * 数据更新时间
     */
    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Date)
    private java.util.Date updateTime;

    @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
    private T extra;

    public static cn.meteor.beyondclouds.modules.search.entity.SearchItem of(cn.meteor.beyondclouds.modules.blog.dto.BlogDetailDTO blogDetail) {
        return null;
    }
}