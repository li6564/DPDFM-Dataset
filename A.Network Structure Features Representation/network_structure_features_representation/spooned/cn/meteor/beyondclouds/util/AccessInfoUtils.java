package cn.meteor.beyondclouds.util;
/**
 *
 * @author meteor
 */
public class AccessInfoUtils {
    public static boolean hasFieldInfo(cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        return (null != accessInfo) && (null != accessInfo.getFieldVisitCount());
    }
}