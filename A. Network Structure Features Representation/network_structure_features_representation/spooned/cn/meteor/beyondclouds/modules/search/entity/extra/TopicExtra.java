package cn.meteor.beyondclouds.modules.search.entity.extra;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
/**
 *
 * @author meteor
 */
@lombok.Data
public class TopicExtra {
    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer referenceCount;
}