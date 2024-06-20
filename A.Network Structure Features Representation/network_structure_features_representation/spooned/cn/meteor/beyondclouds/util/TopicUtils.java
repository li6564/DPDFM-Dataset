package cn.meteor.beyondclouds.util;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.util.Assert;
/**
 *
 * @author meteor
 */
@lombok.extern.slf4j.Slf4j
public class TopicUtils {
    private static final java.util.regex.Pattern topicPattern = java.util.regex.Pattern.compile("(#([^#]+?)#)");

    private static final java.util.regex.Pattern delayPattern = java.util.regex.Pattern.compile("(delay\\((\\d+)\\);)$");

    private static final java.util.regex.Pattern tailLFPattern = java.util.regex.Pattern.compile("(\n+)$");

    public static java.lang.String encodeTopic(java.lang.String str) {
        org.springframework.util.Assert.hasText(str, "str must not be empty");
        str = str.replaceAll(cn.meteor.beyondclouds.util.TopicUtils.tailLFPattern.pattern(), "");
        java.util.regex.Matcher matcher = cn.meteor.beyondclouds.util.TopicUtils.delayPattern.matcher(str);
        if (matcher.find()) {
            str = matcher.replaceAll("");
        }
        str = str.replaceAll(cn.meteor.beyondclouds.util.TopicUtils.tailLFPattern.pattern(), "");
        matcher = cn.meteor.beyondclouds.util.TopicUtils.topicPattern.matcher(str);
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        while (matcher.find()) {
            java.lang.String tmp;
            if (!org.apache.commons.lang3.StringUtils.isBlank(matcher.group(2))) {
                tmp = ((("<a href='/topic/detail/" + matcher.group(2)) + "'>") + matcher.group(1)) + "</a>";
            } else {
                tmp = matcher.group();
            }
            matcher.appendReplacement(buffer, tmp);
        } 
        matcher.appendTail(buffer);
        java.lang.String result = buffer.toString().replace("\n", "<br>");
        return result;
    }

    public static java.util.List<java.lang.String> parseTopics(java.lang.String str) {
        org.springframework.util.Assert.hasText(str, "str must not be empty");
        java.util.regex.Matcher matcher = cn.meteor.beyondclouds.util.TopicUtils.topicPattern.matcher(str);
        java.util.List<java.lang.String> topicNames = new java.util.ArrayList<>();
        while (matcher.find()) {
            if (!org.apache.commons.lang3.StringUtils.isBlank(matcher.group(2))) {
                topicNames.add(matcher.group(2));
            }
        } 
        topicNames = topicNames.stream().collect(java.util.stream.Collectors.collectingAndThen(java.util.stream.Collectors.toCollection(() -> new java.util.HashSet<>()), java.util.ArrayList::new));
        return topicNames;
    }

    public static java.lang.Integer getDelay(java.lang.String str) {
        str = str.replaceAll(cn.meteor.beyondclouds.util.TopicUtils.tailLFPattern.pattern(), "");
        java.util.regex.Matcher matcher = cn.meteor.beyondclouds.util.TopicUtils.delayPattern.matcher(str);
        if (matcher.find()) {
            return java.lang.Integer.valueOf(matcher.group(2));
        } else {
            return null;
        }
    }

    public static void main(java.lang.String[] args) {
        // System.out.println(TopicUtils.parseTopics("#哈哈哈#今#哈哈哈#天你吃饭了吗#吃了#######"));
        java.lang.System.out.println(cn.meteor.beyondclouds.util.TopicUtils.encodeTopic("\n\n#哈哈哈#今#哈哈delay(5);哈#天你吃饭了吗#吃了#######哈哈#a#bdelay(5);\n\n"));
        java.lang.System.out.println(cn.meteor.beyondclouds.util.TopicUtils.getDelay("#哈哈哈#今#哈哈delay(5);哈#天你吃饭了吗#吃了#######哈哈#a#bdelay(5);"));
        // System.out.println(TopicUtils.clearLockedTopics("#哈哈哈#今#哈哈哈#天你吃饭了吗#吃了#######哈哈#a#b", List.of("哈哈哈")));
    }

    /**
     * 删除被锁定的话题
     *
     * @param content
     * @param lockedTopics
     * @return  */
    public static java.lang.String clearLockedTopics(java.lang.String content, java.util.List<java.lang.String> lockedTopics) {
        java.lang.String newContent = content;
        for (java.lang.String topicName : lockedTopics) {
            newContent = newContent.replace(("#" + topicName) + "#", "");
        }
        return cn.meteor.beyondclouds.util.TopicUtils.encodeTopic(newContent);
    }
}