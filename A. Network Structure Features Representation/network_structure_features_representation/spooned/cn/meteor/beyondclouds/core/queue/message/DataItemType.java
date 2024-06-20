package cn.meteor.beyondclouds.core.queue.message;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum DataItemType {

    /**
     * 用户
     */
    USER(cn.meteor.beyondclouds.modules.user.entity.User.class),
    /**
     * 用户统计信息
     */
    USER_STATISTICS(cn.meteor.beyondclouds.modules.user.entity.UserStatistics.class),
    /**
     * 博客
     */
    BLOG(cn.meteor.beyondclouds.modules.blog.entity.Blog.class),
    /**
     * 博客浏览量
     */
    BLOG_VIEW_NUM(cn.meteor.beyondclouds.modules.blog.entity.Blog.class),
    /**
     * 博客评论
     */
    BLOG_COMMENT(cn.meteor.beyondclouds.modules.blog.entity.BlogComment.class),
    /**
     * 项目
     */
    PROJECT(cn.meteor.beyondclouds.modules.project.entity.Project.class),
    /**
     * 项目评论
     */
    PROJECT_COMMENT(cn.meteor.beyondclouds.modules.project.entity.ProjectComment.class),
    /**
     * 话题
     */
    TOPIC(cn.meteor.beyondclouds.modules.project.entity.Project.class),
    /**
     * 动态
     */
    POST(cn.meteor.beyondclouds.modules.post.entity.Post.class),
    /**
     * 动态评论
     */
    POST_COMMENT(cn.meteor.beyondclouds.modules.post.entity.PostComment.class),
    /**
     * 问答
     */
    QUESTION(cn.meteor.beyondclouds.modules.question.entity.Question.class),
    /**
     * 问题回答
     */
    QUESTION_REPLY(cn.meteor.beyondclouds.modules.question.entity.QuestionReply.class),
    /**
     * 回答被评论
     */
    QUESTION_REPLY_COMMENT(cn.meteor.beyondclouds.modules.question.entity.QuestionReplyComment.class),
    /**
     * 问题回答 被采纳
     */
    QUESTION_REPLY_ACCEPT(cn.meteor.beyondclouds.modules.question.entity.QuestionReply.class),
    /**
     * 用户关注
     */
    USER_FOLLOW(cn.meteor.beyondclouds.modules.user.entity.UserFollow.class),
    /**
     * 博客点赞
     */
    BLOG_PRAISE(cn.meteor.beyondclouds.modules.blog.entity.BlogPraise.class),
    /**
     * 项目点赞
     */
    PROJECT_PRAISE(cn.meteor.beyondclouds.modules.project.entity.ProjectPraise.class),
    /**
     * 动态点赞
     */
    POST_PRAISE(cn.meteor.beyondclouds.modules.post.entity.PostPraise.class),
    /**
     * 问题点赞
     */
    QUESTION_PRAISE(cn.meteor.beyondclouds.modules.question.entity.QuestionPraise.class);
    private java.lang.Class<?> classOfItem;

    DataItemType(java.lang.Class<?> classOfItem) {
        this.classOfItem = classOfItem;
    }
}