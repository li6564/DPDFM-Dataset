package cn.meteor.beyondclouds.common.helper;
/**
 * QQ认证辅助类
 *
 * @author 段启岩
 */
public interface IQQAuthenticationHelper {
    /**
     * 通过authorization_code进行认证
     *
     * @param code
     * @return  */
    cn.meteor.beyondclouds.common.dto.QQAuthResultDTO authentication(java.lang.String code) throws cn.meteor.beyondclouds.common.exception.QQAuthenticationException;
}