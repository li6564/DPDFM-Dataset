package cn.meteor.beyondclouds.modules.project.dto;
import lombok.Data;
/**
 * 项目详情
 *
 * @author meteor
 */
@lombok.Data
public class ProjectDetailDTO extends cn.meteor.beyondclouds.modules.project.entity.Project {
    /**
     * 当前用户是否关注作者
     */
    private java.lang.Boolean followedAuthor;

    private java.lang.Boolean praised;

    /**
     * 项目详情
     */
    private java.lang.String content;

    /**
     * 项目详情html
     */
    private java.lang.String contentHtml;
}