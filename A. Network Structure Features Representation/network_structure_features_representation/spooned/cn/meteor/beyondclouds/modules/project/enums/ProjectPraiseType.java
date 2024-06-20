package cn.meteor.beyondclouds.modules.project.enums;
import lombok.Getter;
/**
 *
 * @author meteor
 */
@lombok.Getter
public enum ProjectPraiseType {

    /**
     * 项目点赞
     */
    PROJECT_PRAISE(1),
    /**
     * 项目评论点赞
     */
    PROJECT_COMMENT_PRAISE(2);
    private java.lang.Integer praiseType;

    ProjectPraiseType(java.lang.Integer praiseType) {
        this.praiseType = praiseType;
    }
}