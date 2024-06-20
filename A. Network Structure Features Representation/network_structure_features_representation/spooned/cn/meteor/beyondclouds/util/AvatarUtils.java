package cn.meteor.beyondclouds.util;
import java.io.IOException;
/**
 * 头像工具
 *
 * @author meteor
 */
public class AvatarUtils {
    private static final java.lang.String ID_PREFIX = "identIcon";

    /**
     * 根据userId生成一个头像，颜色随机。
     *
     * @param id
     * @return  * @throws IOException
     */
    public static byte[] create(java.lang.String id) throws java.io.IOException {
        int width = 20;
        int grid = 5;
        int padding = width / 2;
        int size = (width * grid) + width;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D _2d = img.createGraphics();
        _2d.setColor(new java.awt.Color(240, 240, 240));
        _2d.fillRect(0, 0, size, size);
        _2d.setColor(cn.meteor.beyondclouds.util.AvatarUtils.randomColor(80, 200));
        char[] idCharArray = cn.meteor.beyondclouds.util.AvatarUtils.createIndent(id);
        int i = idCharArray.length;
        for (int x = 0; x < java.lang.Math.ceil(grid / 2.0); x++) {
            for (int y = 0; y < grid; y++) {
                if (idCharArray[--i] < 53) {
                    _2d.fillRect(padding + (x * width), padding + (y * width), width, width);
                    if (x < java.lang.Math.floor(grid / 2)) {
                        _2d.fillRect(padding + (((grid - 1) - x) * width), padding + (y * width), width, width);
                    }
                }
            }
        }
        _2d.dispose();
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(img, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static java.awt.Color randomColor(int fc, int bc) {
        java.util.Random random = new java.util.Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(java.lang.Math.abs(bc - fc));
        int g = fc + random.nextInt(java.lang.Math.abs(bc - fc));
        int b = fc + random.nextInt(java.lang.Math.abs(bc - fc));
        return new java.awt.Color(r, g, b);
    }

    private static char[] createIndent(java.lang.String id) {
        int idHash = java.lang.Math.abs(id.hashCode());
        java.math.BigInteger bi_content = new java.math.BigInteger((idHash + "").getBytes());
        java.math.BigInteger bi = new java.math.BigInteger((idHash + cn.meteor.beyondclouds.util.AvatarUtils.ID_PREFIX) + idHash, 36);
        bi = bi.xor(bi_content);
        return bi.toString(10).toCharArray();
    }
}