package cn.meteor.beyondclouds.core.flow;
import lombok.Data;
import lombok.ToString;
/**
 * 访问者信息
 *
 * @author meteor
 */
@lombok.Data
@lombok.ToString
public class AccessInfo {
    /**
     * 访问者IP
     */
    private java.lang.String ipAddress;

    /**
     * 上一次访问网站的时间
     */
    private java.lang.Long latestVisitTime;

    /**
     * 该IP连续访问网站的次数
     */
    private java.lang.Integer visitCount;

    /**
     * 上一次访问目标字段的时间
     */
    private java.lang.Long fieldLatestVisitTime;

    /**
     * 访问该字段的次数
     */
    private java.lang.Integer fieldVisitCount;
}