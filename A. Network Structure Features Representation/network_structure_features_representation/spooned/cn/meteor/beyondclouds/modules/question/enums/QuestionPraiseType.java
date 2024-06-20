package cn.meteor.beyondclouds.modules.question.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum QuestionPraiseType {

    /**
     * 问题点赞
     */
    QUESTION_PRAISE(1),
    /**
     * 问题回复点赞
     */
    QUESTION_REPLY_PRAISE(2),
    /**
     * 问题回复评论点赞
     */
    QUESTION_REPLY_COMMENT_PRAISE(3);
    private java.lang.Integer praiseType;

    QuestionPraiseType(java.lang.Integer praiseType) {
        this.praiseType = praiseType;
    }
}