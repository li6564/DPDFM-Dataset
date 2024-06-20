package cn.meteor.beyondclouds.modules.mail.util;
/**
 *
 * @author meteor
 */
public class EmailUtils {
    /**
     * 生成激活邮件
     *
     * @param activeUrl
     * @return  */
    public static java.lang.String generateActiveMail(java.lang.String activeUrl) {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<title>");
        builder.append("云里云外开源社区激活邮件");
        builder.append("</title>");
        builder.append("<meta charset=\"UTF-8\">");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("欢迎注册云里云外开源社区,请点击以下链接激活<br>");
        builder.append("<a href=\"");
        builder.append(activeUrl);
        builder.append("\">");
        builder.append(activeUrl);
        builder.append("</a>");
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }

    /**
     * 生成验证码邮件
     *
     * @param verifyCode
     * @return  */
    public static java.lang.String generateVerifyCodeMail(java.lang.String verifyCode) {
        java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<title>");
        builder.append("云里云外开源社区激活邮件");
        builder.append("</title>");
        builder.append("<meta charset=\"UTF-8\">");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("云里云外开源社区,您的验证码为<strong>");
        builder.append(verifyCode);
        builder.append("</strong>");
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }
}