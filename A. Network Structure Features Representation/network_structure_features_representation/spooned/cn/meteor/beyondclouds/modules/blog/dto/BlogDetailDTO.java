package cn.meteor.beyondclouds.modules.blog.dto;
import lombok.Data;
/**
 *
 * @author gaoTong
 * @date 2020/2/1 9:26
 */
@lombok.Data
public class BlogDetailDTO extends cn.meteor.beyondclouds.modules.blog.entity.Blog {
    private java.lang.Boolean followedAuthor;

    private java.lang.Boolean praised;

    private java.lang.String content;

    private java.lang.String contentHtml;
}