package cn.meteor.beyondclouds.modules.topic.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum TopicAccessWay {

    ID("id", "topic_id"),
    NAME("name", "binary topic_name");
    private java.lang.String by;

    private java.lang.String fieldName;

    TopicAccessWay(java.lang.String by, java.lang.String fieldName) {
        this.by = by;
        this.fieldName = fieldName;
    }
}