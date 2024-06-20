package cn.meteor.beyondclouds.modules.post.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum PostPraiseType {

    /**
     * 动态点赞
     */
    POST_PRAISE(1),
    /**
     * 给动态评论点赞
     */
    POST_COMMENT_PRAISE(2);
    private java.lang.Integer praiseType;

    PostPraiseType(java.lang.Integer praiseType) {
        this.praiseType = praiseType;
    }
}