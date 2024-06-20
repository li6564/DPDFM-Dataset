package cn.meteor.beyondclouds.modules.message.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum MessageType {

    /**
     * 系统消息
     */
    SYS(0),
    /**
     * 关注
     */
    FOLLOW(1),
    /**
     * 博客评论
     */
    BLOG_COMMENT(2),
    /**
     * 项目评论
     */
    PROJECT_COMMENT(3),
    /**
     * 问题回复
     */
    QUESTION_REPLY(4),
    /**
     * 回答采纳
     */
    QUESTION_REPLY_ACCEPTED(5),
    /**
     * 动态评论
     */
    POST_COMMENT(6),
    /**
     * 博客点赞
     */
    BLOG_PRAISE(7),
    /**
     * 项目点赞
     */
    PROJECT_PRAISE(8),
    /**
     * 动态点赞
     */
    POST_PRAISE(9),
    /**
     * 问题点赞
     */
    QUESTION_PRAISE(10);
    private java.lang.Integer type;

    MessageType(java.lang.Integer type) {
        this.type = type;
    }
}