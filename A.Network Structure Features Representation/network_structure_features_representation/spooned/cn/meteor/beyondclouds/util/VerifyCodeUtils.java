package cn.meteor.beyondclouds.util;
/**
 * 短信验证码工具类
 *
 * @author meteor
 */
public final class VerifyCodeUtils {
    private static final java.util.Random random = new java.util.Random();

    /**
     * 生成随机验证码
     *
     * @return  */
    public static java.lang.String randomVerifyCode() {
        return java.lang.String.format("%d%d%d%d%d%d", cn.meteor.beyondclouds.util.VerifyCodeUtils.randomNumber(), cn.meteor.beyondclouds.util.VerifyCodeUtils.randomNumber(), cn.meteor.beyondclouds.util.VerifyCodeUtils.randomNumber(), cn.meteor.beyondclouds.util.VerifyCodeUtils.randomNumber(), cn.meteor.beyondclouds.util.VerifyCodeUtils.randomNumber(), cn.meteor.beyondclouds.util.VerifyCodeUtils.randomNumber());
    }

    private static int randomNumber() {
        return cn.meteor.beyondclouds.util.VerifyCodeUtils.random.nextInt(9);
    }
}