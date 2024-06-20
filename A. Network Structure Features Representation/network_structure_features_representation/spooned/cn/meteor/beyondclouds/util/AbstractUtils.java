package cn.meteor.beyondclouds.util;
import org.springframework.util.Assert;
/**
 * 摘要提取工具
 *
 * @author meteor
 */
public class AbstractUtils {
    /**
     * 提取摘要
     *
     * @param content
     * 		待提取的内容
     * @param count
     * 		要提取的摘要的字数
     * @return  */
    public static java.lang.String extractWithoutHtml(java.lang.String content, int count) {
        org.springframework.util.Assert.hasText(content, "content must not be empty");
        // 去除内容中的HTML标签
        java.lang.String cleanContent = content.replaceAll("<[^>]+>|</[^>]+>", "");
        cleanContent = cleanContent.replaceAll(" ", "");
        // 判断内容是否有20个字
        if (cleanContent.length() <= count) {
            return cleanContent;
        } else {
            return cleanContent.substring(0, count) + "...";
        }
    }
}