package cn.meteor.beyondclouds.modules.blog.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum BlogPraiseType {

    /**
     * 博客点赞
     */
    BLOG_PRAISE(1),
    /**
     * 博客评论点赞
     */
    BLOG_COMMENT_PRAISE(2);
    private java.lang.Integer praiseType;

    BlogPraiseType(java.lang.Integer praiseType) {
        this.praiseType = praiseType;
    }
}