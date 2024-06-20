package cn.meteor.beyondclouds.modules.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
public class AuthenticationServiceImpl implements cn.meteor.beyondclouds.modules.user.service.IAuthenticationService {
    private cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService userAuthLocalService;

    private cn.meteor.beyondclouds.common.helper.IQQAuthenticationHelper iqqAuthenticationHelper;

    private cn.meteor.beyondclouds.modules.user.service.IUserAuthAppService userAuthAppService;

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.core.redis.TokenManager tokenManager;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthenticationServiceImpl(cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService userAuthLocalService, cn.meteor.beyondclouds.common.helper.IQQAuthenticationHelper iqqAuthenticationHelper, cn.meteor.beyondclouds.modules.user.service.IUserAuthAppService userAuthAppService, cn.meteor.beyondclouds.modules.user.service.IUserService userService, cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.userAuthLocalService = userAuthLocalService;
        this.iqqAuthenticationHelper = iqqAuthenticationHelper;
        this.userAuthAppService = userAuthAppService;
        this.userService = userService;
        this.redisHelper = redisHelper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setTokenManager(cn.meteor.beyondclouds.core.redis.TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO localAuthentication(java.lang.String account, java.lang.String password, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) throws cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException {
        // 1. 查找用户是否存在
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getByAccount(account);
        if (null == userAuthLocal) {
            messageQueueService.sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage.loginFailureMessage(cn.meteor.beyondclouds.util.SubjectUtils.getSubject(), "LOCAL_ACCOUNT", (account + "<@>") + password));
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.USER_NOT_FOUND);
        }
        cn.meteor.beyondclouds.modules.user.entity.User user = userService.getById(userAuthLocal.getUserId());
        if (cn.meteor.beyondclouds.modules.user.enums.UserStatus.DISABLED.getStatus().equals(user.getStatus())) {
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_DISABLED);
        }
        // 2. 判断账号状态
        if (null == userAuthLocal.getStatus()) {
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.USER_AUTH_DISABLED);
        }
        if (cn.meteor.beyondclouds.modules.user.enums.AuthStatus.DISABLED.getStatus() == userAuthLocal.getStatus()) {
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.USER_AUTH_DISABLED);
        }
        if (cn.meteor.beyondclouds.modules.user.enums.AuthStatus.WAIT_FOR_ACTIVE.getStatus() == userAuthLocal.getStatus()) {
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.ACCOUNT_NOT_ACTIVE);
        }
        // 3. 判断密码是否正确
        java.lang.String encodedPassword = cn.meteor.beyondclouds.util.Md5Utils.encode(password);
        if (!encodedPassword.equals(userAuthLocal.getPassword())) {
            messageQueueService.sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage.loginFailureMessage(cn.meteor.beyondclouds.util.SubjectUtils.getSubject(), "LOCAL_ACCOUNT", (account + "<@>") + password));
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.PASSWORD_NOT_MATCHED);
        }
        // 根据userId生成token并返回
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO authenticationResultDTO = makeAuthenticationResult(userAuthLocal.getUserId(), clientType);
        sendUserLoginMessage(userAuthLocal, clientType);
        return authenticationResultDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO qqAuthentication(java.lang.String code, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) throws cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException {
        cn.meteor.beyondclouds.common.dto.QQAuthResultDTO qqAuthResult;
        try {
            // 1.进行QQ认证，获取认证结果
            qqAuthResult = iqqAuthenticationHelper.authentication(code);
            // 2.判断是否为新用户,若是新用户
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthApp> userAuthAppQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            userAuthAppQueryWrapper.eq("app_user_id", qqAuthResult.getOpenId());
            cn.meteor.beyondclouds.modules.user.entity.UserAuthApp userAuthApp = userAuthAppService.getOne(userAuthAppQueryWrapper);
            java.lang.String userId;
            // 若该QQ为新用户，以前未注册过，则进行注册逻辑
            if (null == userAuthApp) {
                userId = userService.qqRegister(qqAuthResult).getUserId();
            } else {
                userId = userAuthApp.getUserId();
            }
            // 根据userId生成token并返回
            return makeAuthenticationResult(userId, clientType);
        } catch (cn.meteor.beyondclouds.common.exception.QQAuthenticationException e) {
            e.printStackTrace();
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.QQ_AUTH_ERROR);
        }
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO smsAuthentication(java.lang.String mobile, java.lang.String verifyCode, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException, cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException {
        // 1.检查验证码是否正确
        java.lang.String realVerifyCode = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        if (org.springframework.util.StringUtils.isEmpty(realVerifyCode) || (!realVerifyCode.equals(verifyCode))) {
            messageQueueService.sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage.loginFailureMessage(cn.meteor.beyondclouds.util.SubjectUtils.getSubject(), "LOCAL_SMS", (mobile + "<@>") + verifyCode));
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.REG_VERIFY_CODE_ERROR);
        }
        // 2. 查找认证信息是否存在
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getByMobile(mobile);
        if (null == userAuthLocal) {
            messageQueueService.sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage.loginFailureMessage(cn.meteor.beyondclouds.util.SubjectUtils.getSubject(), "LOCAL_SMS", (mobile + "<@>") + verifyCode));
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.modules.user.enums.AuthenticationErrorCode.USER_NOT_FOUND);
        }
        // 删除验证码
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        // 根据userId生成token并返回
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO authenticationResultDTO = makeAuthenticationResult(userAuthLocal.getUserId(), clientType);
        sendUserLoginMessage(userAuthLocal, clientType);
        return authenticationResultDTO;
    }

    /**
     * 制造认证结果
     *
     * @param userId
     * @param clientType
     * @return  */
    private cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO makeAuthenticationResult(java.lang.String userId, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) {
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO result = new cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO();
        cn.meteor.beyondclouds.modules.user.dto.TokenResult tokenResult = tokenManager.generateToken(userId, clientType);
        result.setUserId(userId);
        result.setAccessToken(tokenResult.getAccessToken());
        result.setRefreshToken(tokenResult.getRefreshToken());
        return result;
    }

    /**
     * 发送用户登录消息
     *
     * @param userAuthLocal
     * @param clientType
     */
    private void sendUserLoginMessage(cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal, cn.meteor.beyondclouds.modules.user.enums.ClientType clientType) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        messageQueueService.sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage.loginMessage(cn.meteor.beyondclouds.core.authentication.Subject.authenticated(userAuthLocal.getUserId(), clientType.getType(), subject.getIpAddress()), userAuthLocal));
    }

    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> getAuths(java.lang.String userId) {
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> auths = new java.util.ArrayList<>();
        // 查询local表里的认证信息
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryLocalAuths = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryLocalAuths.eq("user_id", userId);
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> localAuths = userAuthLocalService.list(queryLocalAuths);
        for (cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal localAuth : localAuths) {
            cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO auth = new cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO();
            auth.setAuthId(localAuth.getUaLocalId());
            auth.setAuthType(cn.meteor.beyondclouds.modules.user.enums.AuthType.LOCAL.getType());
            auth.setAccountType(localAuth.getAccountType());
            auth.setAccount(localAuth.getAccount());
            auth.setCreateTime(localAuth.getCreateTime());
            auth.setUpdateTime(localAuth.getUpdateTime());
            auths.add(auth);
        }
        // 查询第三方表里的认证信息
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper queryAppAuths = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper();
        queryAppAuths.eq("user_id", userId);
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserAuthApp> appAuths = userAuthAppService.list(queryAppAuths);
        for (cn.meteor.beyondclouds.modules.user.entity.UserAuthApp appAuth : appAuths) {
            cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO auth = new cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO();
            auth.setAuthId(appAuth.getUaAppId());
            auth.setAuthType(cn.meteor.beyondclouds.modules.user.enums.AuthType.APP.getType());
            auth.setAccountType(appAuth.getAppType());
            auth.setAccount(appAuth.getAppUserId());
            auth.setCreateTime(appAuth.getCreateTime());
            auth.setUpdateTime(appAuth.getUpdateTime());
            auths.add(auth);
        }
        return auths;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO refreshToken(java.lang.String refreshToken) throws cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException {
        cn.meteor.beyondclouds.modules.user.dto.TokenInfo tokenInfo = tokenManager.getRefreshTokenInfo(refreshToken);
        if (null == tokenInfo) {
            throw new cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException(cn.meteor.beyondclouds.core.emuns.AuthorizationErrorCode.REFRESH_TOKEN_VERIFY_FAILURE);
        }
        return makeAuthenticationResult(tokenInfo.getUserId(), cn.meteor.beyondclouds.modules.user.enums.ClientType.valueOf(tokenInfo.getClientType()));
    }
}