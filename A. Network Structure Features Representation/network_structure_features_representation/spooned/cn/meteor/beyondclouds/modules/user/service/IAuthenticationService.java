package cn.meteor.beyondclouds.modules.user.service;
/**
 * 认证服务
 *
 * @author meteor
 */
public interface IAuthenticationService {
    /**
     * 根据手机号和密码认证用户
     *
     * @param account
     * @param password
     * @param clientType
     * @return  */
    cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO localAuthentication(java.lang.String account, java.lang.String password, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) throws cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException;

    /**
     * QQ认证
     *
     * @param code
     * @param clientType
     * @return  */
    cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO qqAuthentication(java.lang.String code, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) throws cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException;

    /**
     * 短信验证登陆
     *
     * @param mobile
     * @param verifyCode
     * @param clientType
     * @return  */
    cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO smsAuthentication(java.lang.String mobile, java.lang.String verifyCode, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException, cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException;

    /**
     * 获取我的认证列表
     *
     * @param userId
     * @return  */
    java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> getAuths(java.lang.String userId);

    /**
     * 刷新token
     *
     * @param refreshToken
     * @return  */
    cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO refreshToken(java.lang.String refreshToken) throws cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException;
}