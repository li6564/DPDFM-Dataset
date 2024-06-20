package cn.meteor.beyondclouds.modules.post.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class PostDTO {
    private static final long serialVersionUID = 1L;

    private java.lang.String postId;

    private java.lang.String userId;

    private java.lang.Integer type;

    private java.lang.String content;

    private java.lang.String[] pictures;

    private java.lang.String video;

    private java.lang.String userAvatar;

    private java.lang.String userNick;

    private java.lang.Integer commentNumber;

    private java.lang.Integer status;

    private java.lang.Integer praiseNum;

    private java.lang.Boolean praised;

    private java.util.Date createTime;

    private java.util.Date updateTime;

    private java.lang.Boolean followedAuthor;
}