package cn.meteor.beyondclouds.util;
/**
 * Http请求辅助工具
 *
 * @author 段启岩
 */
public class RequestUtils {
    private static final java.lang.String STR_UN_KNOW = "unknown";

    /**
     * 获取访问者的IP地址
     *
     * @param request
     * @return  */
    public static java.lang.String getIpAddr(javax.servlet.http.HttpServletRequest request) {
        java.lang.String ip = request.getHeader("x-forwarded-for");
        if (((ip == null) || (ip.length() == 0)) || cn.meteor.beyondclouds.util.RequestUtils.STR_UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (((ip == null) || (ip.length() == 0)) || cn.meteor.beyondclouds.util.RequestUtils.STR_UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (((ip == null) || (ip.length() == 0)) || cn.meteor.beyondclouds.util.RequestUtils.STR_UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}