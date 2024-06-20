package cn.meteor.beyondclouds.util;
import org.apache.commons.codec.digest.Md5Crypt;
/**
 *
 * @author meteor
 */
public class Md5Utils {
    public static java.lang.String encode(java.lang.String str) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(str.getBytes());
            java.lang.String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if ((temp < 16) && (temp >= 0)) {
                    hexString = (hexString + "0") + java.lang.Integer.toHexString(temp);
                } else {
                    hexString = hexString + java.lang.Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static java.lang.String encode(byte[] data) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(data);
            java.lang.String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if ((temp < 16) && (temp >= 0)) {
                    hexString = (hexString + "0") + java.lang.Integer.toHexString(temp);
                } else {
                    hexString = hexString + java.lang.Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}