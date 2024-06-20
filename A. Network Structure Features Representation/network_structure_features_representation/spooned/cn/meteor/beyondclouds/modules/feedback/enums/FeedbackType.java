package cn.meteor.beyondclouds.modules.feedback.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum FeedbackType {

    /**
     * 意见反馈
     */
    FEEDBACK(1),
    /**
     * 投诉举报
     */
    COMPLAINT(2);
    private java.lang.Integer type;

    FeedbackType(java.lang.Integer type) {
        this.type = type;
    }
}