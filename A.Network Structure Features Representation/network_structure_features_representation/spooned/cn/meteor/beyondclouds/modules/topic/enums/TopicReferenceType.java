package cn.meteor.beyondclouds.modules.topic.enums;
import lombok.Getter;
/**
 * 话题引用类型
 *
 * @author meteor
 */
@lombok.Getter
public enum TopicReferenceType {

    BLOG_REFERENCE(0),
    PROJECT_REFERENCE(1),
    QUESTION_REFERENCE(2),
    POST_REFERENCE(3);
    private int type;

    TopicReferenceType(int type) {
        this.type = type;
    }
}