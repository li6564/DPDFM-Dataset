package cn.meteor.beyondclouds.modules.search.util;
/**
 *
 * @author meteor
 */
public class TopicScoreUtils {
    public static double calculateScore(java.lang.String topicName, java.lang.String keywords) {
        double keywordLen = keywords.length();
        double topicLen = topicName.length();
        double score = keywordLen / topicLen;
        return new java.math.BigDecimal(score).setScale(2, java.math.RoundingMode.DOWN).doubleValue();
    }

    public static double setScale(double value) {
        return new java.math.BigDecimal(value).setScale(2, java.math.RoundingMode.DOWN).doubleValue();
    }
}