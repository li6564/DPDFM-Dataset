package cn.meteor.beyondclouds.util;
import org.springframework.util.Assert;
/**
 *
 * @author meteor
 */
public class CommentUtils {
    public static java.lang.String encodeComment(java.lang.String comment) {
        org.springframework.util.Assert.hasText(comment, "comment must has text.");
        return comment.replace("\n", "<br>");
    }
}