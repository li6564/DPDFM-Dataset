package cn.meteor.beyondclouds.util;
/**
 * UUID工具类
 *
 * @author meteor
 */
public class UUIDUtils {
    private static final java.util.Random rand = new java.util.Random();

    /**
     * 生成随机UUID
     *
     * @return  */
    public static java.lang.String randomUUID() {
        return java.util.UUID.randomUUID().toString().toLowerCase();
    }

    /**
     * 生成随机UUID
     *
     * @return  */
    public static java.lang.String randomToken() {
        java.lang.String uuid1 = cn.meteor.beyondclouds.util.UUIDUtils.randomUUID().replace("-", "");
        java.lang.String uuid2 = cn.meteor.beyondclouds.util.UUIDUtils.randomUUID().replace("-", "");
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        for (int i = 0; i < uuid1.length(); i++) {
            int random = cn.meteor.beyondclouds.util.UUIDUtils.rand.nextInt(2);
            builder.append(random == 0 ? java.lang.Character.toUpperCase(uuid1.charAt(i)) : java.lang.Character.toLowerCase(uuid1.charAt(i)));
        }
        for (int i = 0; i < uuid2.length(); i++) {
            int random = cn.meteor.beyondclouds.util.UUIDUtils.rand.nextInt(2);
            builder.append(random == 0 ? java.lang.Character.toUpperCase(uuid2.charAt(i)) : java.lang.Character.toLowerCase(uuid2.charAt(i)));
        }
        return builder.toString();
    }
}