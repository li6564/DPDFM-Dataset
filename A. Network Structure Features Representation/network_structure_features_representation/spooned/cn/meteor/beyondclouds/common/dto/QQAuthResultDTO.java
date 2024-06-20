package cn.meteor.beyondclouds.common.dto;
import lombok.Data;
/**
 * QQ 登录结果封装
 *
 * @author meteor
 */
@lombok.Data
public class QQAuthResultDTO {
    /**
     * token
     */
    private java.lang.String accessToken;

    /**
     * qq唯一ID
     */
    private java.lang.String openId;

    /**
     * 性别
     */
    private java.lang.Integer gender;

    /**
     * 昵称
     */
    private java.lang.String nickName;

    /**
     * 头像
     */
    private java.lang.String avatar;
}