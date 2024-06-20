package cn.meteor.beyondclouds.modules.user.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Service
public class UserServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.user.mapper.UserMapper, cn.meteor.beyondclouds.modules.user.entity.User> implements cn.meteor.beyondclouds.modules.user.service.IUserService {
    private cn.meteor.beyondclouds.common.helper.IOssHelper ossHelper;

    private cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService userAuthLocalService;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    private cn.meteor.beyondclouds.modules.user.service.IUserAuthAppService userAuthAppService;

    private cn.meteor.beyondclouds.modules.mail.service.IMailService mailService;

    private cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService;

    private cn.meteor.beyondclouds.modules.user.mapper.UserMapper userMapper;

    private cn.meteor.beyondclouds.modules.user.generator.UserNickGenerator userNickGenerator;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    private cn.meteor.beyondclouds.core.redis.TokenManager tokenManager;

    private static final long MILLS_OF_DAY = ((1000 * 60) * 60) * 24;

    @org.springframework.beans.factory.annotation.Autowired
    public UserServiceImpl(cn.meteor.beyondclouds.modules.user.service.IUserAuthLocalService userAuthLocalService, cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper, cn.meteor.beyondclouds.modules.user.service.IUserAuthAppService userAuthAppService, cn.meteor.beyondclouds.modules.mail.service.IMailService mailService) {
        this.userAuthLocalService = userAuthLocalService;
        this.redisHelper = redisHelper;
        this.userAuthAppService = userAuthAppService;
        this.mailService = mailService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setTokenManager(cn.meteor.beyondclouds.core.redis.TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserNickGenerator(cn.meteor.beyondclouds.modules.user.generator.UserNickGenerator userNickGenerator) {
        this.userNickGenerator = userNickGenerator;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setOssHelper(cn.meteor.beyondclouds.common.helper.IOssHelper ossHelper) {
        this.ossHelper = ossHelper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserFollowService(cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userFollowService = userFollowService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserMapper(cn.meteor.beyondclouds.modules.user.mapper.UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setMessageQueueService(cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService) {
        this.messageQueueService = messageQueueService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserStatisticsService(cn.meteor.beyondclouds.modules.user.service.IUserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @java.lang.Override
    public void registerByMobile(java.lang.String mobile, java.lang.String password, java.lang.String verifyCode) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 1.检查验证码是否正确
        java.lang.String realVerifyCode = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        if (org.springframework.util.StringUtils.isEmpty(realVerifyCode) || (!realVerifyCode.equals(verifyCode))) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.REG_VERIFY_CODE_ERROR);
        }
        // 删除验证码
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        // 2. 检测该手机号是否被注册
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("account", mobile);
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getOne(queryWrapper);
        if (null != userAuthLocal) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.MOBILE_REGISTERED);
        }
        // 生成头像
        java.lang.String avatar;
        try {
            avatar = generateAvatarAndSave(mobile);
        } catch (java.lang.Exception e) {
            avatar = "https://beyondclouds.oss-cn-beijing.aliyuncs.com/avatar/d389c748-f087-4d8e-a138-6756af8790b1.jpeg";
        }
        // 3. 创建用户
        cn.meteor.beyondclouds.modules.user.entity.User user = new cn.meteor.beyondclouds.modules.user.entity.User();
        user.setNickName(userNickGenerator.next());
        user.setSignature("该用户很懒，还没设置签名");
        user.setBirthday(new java.util.Date());
        user.setUserAvatar(avatar);
        user.setMobile(mobile);
        save(user);
        // 创建用户统计信息
        createUserStatistics(user.getUserId());
        // 4. 创建认证信息
        userAuthLocal = new cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal();
        userAuthLocal.setUserId(user.getUserId());
        userAuthLocal.setAccount(mobile);
        // 将认证状态设置为正常
        userAuthLocal.setStatus(cn.meteor.beyondclouds.modules.user.enums.AuthStatus.NORMAL.getStatus());
        // 将账号类型设置为手机号
        userAuthLocal.setAccountType(cn.meteor.beyondclouds.modules.user.enums.AccountType.MOBILE.getType());
        userAuthLocal.setPassword(cn.meteor.beyondclouds.util.Md5Utils.encode(password));
        userAuthLocalService.save(userAuthLocal);
    }

    /**
     * 创建用户统计信息表
     *
     * @param userId
     */
    private void createUserStatistics(java.lang.String userId) {
        cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics = new cn.meteor.beyondclouds.modules.user.entity.UserStatistics();
        userStatistics.setUserId(userId);
        userStatisticsService.save(userStatistics);
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void registerByEmail(java.lang.String email, java.lang.String password) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 1. 检测该邮箱是否被注册
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        queryWrapper.eq("account", email);
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getOne(queryWrapper);
        if (null != userAuthLocal) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.EMAIL_REGISTERED);
        }
        // 生成头像
        java.lang.String avatar;
        try {
            avatar = generateAvatarAndSave(email);
        } catch (java.lang.Exception e) {
            avatar = "https://beyondclouds.oss-cn-beijing.aliyuncs.com/avatar/d389c748-f087-4d8e-a138-6756af8790b1.jpeg";
        }
        // 3. 创建用户
        cn.meteor.beyondclouds.modules.user.entity.User user = new cn.meteor.beyondclouds.modules.user.entity.User();
        user.setNickName(userNickGenerator.next());
        user.setSignature("默认签名");
        user.setBirthday(new java.util.Date());
        user.setUserAvatar(avatar);
        user.setEmail(email);
        save(user);
        // 创建用户统计信息
        createUserStatistics(user.getUserId());
        // 4. 创建并保存认证信息
        userAuthLocal = new cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal();
        userAuthLocal.setUserId(user.getUserId());
        userAuthLocal.setAccount(email);
        // 将认证状态设置为待激活
        userAuthLocal.setStatus(cn.meteor.beyondclouds.modules.user.enums.AuthStatus.WAIT_FOR_ACTIVE.getStatus());
        // 将账号类型设置为邮箱账号
        userAuthLocal.setAccountType(cn.meteor.beyondclouds.modules.user.enums.AccountType.EMAIL.getType());
        userAuthLocal.setPassword(cn.meteor.beyondclouds.util.Md5Utils.encode(password));
        userAuthLocalService.save(userAuthLocal);
        // 5.生成激活码并存入redis,有效时间为24小时
        generateAndSenActiveMail(email);
    }

    private void generateAndSenActiveMail(java.lang.String email) {
        // 生成激活码并存入redis
        java.lang.String activeCode = cn.meteor.beyondclouds.util.UUIDUtils.randomUUID();
        redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.EMAIL_ACTIVE_CODE(activeCode), email, (24 * 60) * 60);
        // 激活邮件
        java.lang.String activeUrl = "http://opensource.yundingshuyuan.com/api/user/active/" + activeCode;
        cn.meteor.beyondclouds.modules.mail.dto.EmailDTO emailDTO = new cn.meteor.beyondclouds.modules.mail.dto.EmailDTO("13546386889@163.com", email, "云里云外激活邮件", cn.meteor.beyondclouds.modules.mail.util.EmailUtils.generateActiveMail(activeUrl));
        mailService.sendHtmlMail(emailDTO);
    }

    @java.lang.Override
    public void updateNickName(java.lang.String userId, java.lang.String nickName) {
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper updateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper();
        updateWrapper.set("nick_name", nickName);
        update(updateWrapper);
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.entity.User qqRegister(cn.meteor.beyondclouds.common.dto.QQAuthResultDTO qqAuthResult) {
        // 1.创建用户
        cn.meteor.beyondclouds.modules.user.entity.User user = new cn.meteor.beyondclouds.modules.user.entity.User();
        user.setNickName(qqAuthResult.getNickName());
        user.setSignature("默认签名");
        user.setBirthday(new java.util.Date());
        user.setGender(qqAuthResult.getGender());
        user.setUserAvatar(qqAuthResult.getAvatar());
        save(user);
        // 创建用户统计信息
        createUserStatistics(user.getUserId());
        // 2.创建第三方认证信息
        cn.meteor.beyondclouds.modules.user.entity.UserAuthApp userAuthApp = new cn.meteor.beyondclouds.modules.user.entity.UserAuthApp();
        userAuthApp.setUserId(user.getUserId());
        userAuthApp.setAppUserId(qqAuthResult.getOpenId());
        userAuthApp.setAppType(cn.meteor.beyondclouds.modules.user.enums.ThirdPartyAppType.QQ.ordinal());
        userAuthApp.setAppAccessToken(qqAuthResult.getAccessToken());
        userAuthAppService.save(userAuthApp);
        return user;
    }

    @java.lang.Override
    public void alterBaseInfo(cn.meteor.beyondclouds.modules.user.entity.User user) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 查询旧数据
        cn.meteor.beyondclouds.modules.user.entity.User userIdDb = getById(user.getUserId());
        boolean userNickUpdate = (!org.springframework.util.StringUtils.isEmpty(user.getNickName())) && (!user.getNickName().equals(userIdDb.getNickName()));
        boolean userAvatarUpdate = (!org.springframework.util.StringUtils.isEmpty(user.getUserAvatar())) && (!user.getUserAvatar().equals(userIdDb.getUserAvatar()));
        // 查询是否到达本月修改上限以及昵称是否重复
        if (userNickUpdate) {
            java.util.Date nickModifyStartTime = userIdDb.getNickModifyStartTime();
            java.lang.Integer nickModifyCount = userIdDb.getNickModifyCount();
            boolean canModifyNick;
            // 如果count等于0或nickModifyStartTime为null，则重置nickModifyStartTime为当前时间，nickModifyCount设置为1
            if ((0 == nickModifyCount) || (nickModifyStartTime == null)) {
                nickModifyStartTime = new java.util.Date();
                nickModifyCount = 1;
                canModifyNick = true;
            } else if ((java.lang.System.currentTimeMillis() - nickModifyStartTime.getTime()) > (cn.meteor.beyondclouds.modules.user.service.impl.UserServiceImpl.MILLS_OF_DAY * 30)) {
                // 判断本月时间是否已经过去，过去则重置
                nickModifyStartTime = new java.util.Date();
                nickModifyCount = 1;
                canModifyNick = true;
            } else // 判断本月修改次数是否超过3次
            if (nickModifyCount >= 3) {
                throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.NICK_MODIFY_UPPER_LIMIT);
            } else {
                canModifyNick = true;
                nickModifyCount = nickModifyCount + 1;
            }
            if (canModifyNick) {
                com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.User> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
                queryWrapper.eq("nick_name", user.getNickName());
                if (null != getOne(queryWrapper)) {
                    throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_NICK_NAME_USED);
                }
                // 更新昵称修改次数和时间
                userIdDb.setNickModifyStartTime(nickModifyStartTime);
                userIdDb.setNickModifyCount(nickModifyCount);
                updateById(userIdDb);
            }
        }
        updateById(user);
        if (userNickUpdate) {
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.userNickUpdateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER, user.getUserId()));
        }
        if (userAvatarUpdate) {
            messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.userAvatarUpdateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER, user.getUserId()));
        }
        // 发送消息到消息队列
        messageQueueService.sendDataItemChangeMessage(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage.updateMessage(cn.meteor.beyondclouds.core.queue.message.DataItemType.USER, user.getUserId()));
    }

    @java.lang.Override
    public void alterPassword(java.lang.String mobile, java.lang.String password, java.lang.String verifyCode) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 1.检查验证码是否正确
        java.lang.String realVerifyCode = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        if (org.springframework.util.StringUtils.isEmpty(realVerifyCode) || (!realVerifyCode.equals(verifyCode))) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.REG_VERIFY_CODE_ERROR);
        }
        // 2.删除验证码
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        // 3.判断手机号是否注册
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getByAccount(mobile);
        if (null == userAuthLocal) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.NON_LOCAL_AUTH_INFO);
        }
        // 4.更新所有本地认证信息里面的密码
        com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper updateWrapper = new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper();
        updateWrapper.set("password", cn.meteor.beyondclouds.util.Md5Utils.encode(password));
        updateWrapper.eq("user_id", userAuthLocal.getUserId());
        userAuthLocalService.update(updateWrapper);
    }

    @java.lang.Override
    public void activeAccount(java.lang.String activeCode) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        org.springframework.util.Assert.hasText(activeCode, "activeCode must not be empty");
        // 1.校验激活码是否正确
        java.lang.String email = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.EMAIL_ACTIVE_CODE(activeCode));
        if (null == email) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.INVALID_ACTIVE_CODE);
        }
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> userAuthLocalQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userAuthLocalQueryWrapper.eq("account", email);
        userAuthLocalQueryWrapper.eq("account_type", cn.meteor.beyondclouds.modules.user.enums.AccountType.EMAIL.getType());
        userAuthLocalQueryWrapper.eq("status", cn.meteor.beyondclouds.modules.user.enums.AuthStatus.WAIT_FOR_ACTIVE.getStatus());
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getOne(userAuthLocalQueryWrapper);
        if (null == userAuthLocal) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.INVALID_ACTIVE_CODE);
        }
        // 2.激活账号
        userAuthLocal.setStatus(cn.meteor.beyondclouds.modules.user.enums.AuthStatus.NORMAL.getStatus());
        userAuthLocalService.updateById(userAuthLocal);
        // 3.删除激活码
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.EMAIL_ACTIVE_CODE(activeCode));
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void bindEMail(java.lang.String email, java.lang.String verifyCode, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 1.检测验证码
        java.lang.String realVerifyCode = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.EMAIL_VERIFY_CODE(email));
        if (org.springframework.util.StringUtils.isEmpty(realVerifyCode) || (!realVerifyCode.equals(verifyCode))) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.BINDING_EMAIL_VERIFY_CODE_ERROR);
        }
        // 2.删除验证码
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.EMAIL_VERIFY_CODE(email));
        // 3.检测该邮箱是否已被占用
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getByAccount(email);
        if (null != userAuthLocal) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.EMAIL_REGISTERED);
        }
        // 4.从其他本地认证里面查找用户密码
        java.lang.String password = getPasswordInUserAuthLocal(userId);
        // 5.绑定邮箱
        userAuthLocal = new cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal();
        userAuthLocal.setUserId(userId);
        userAuthLocal.setAccount(email);
        userAuthLocal.setPassword(password);
        userAuthLocal.setAccountType(cn.meteor.beyondclouds.modules.user.enums.AccountType.EMAIL.getType());
        userAuthLocal.setStatus(cn.meteor.beyondclouds.modules.user.enums.AuthStatus.NORMAL.getStatus());
        userAuthLocalService.save(userAuthLocal);
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = java.lang.Exception.class)
    @java.lang.Override
    public void bindMobile(java.lang.String mobile, java.lang.String verifyCode, java.lang.String userId) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        // 1.检测验证码
        java.lang.String realVerifyCode = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        if (org.springframework.util.StringUtils.isEmpty(realVerifyCode) || (!realVerifyCode.equals(verifyCode))) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.BINDING_MOBILE_VERIFY_CODE_ERROR);
        }
        // 2.删除验证码
        redisHelper.del(cn.meteor.beyondclouds.core.redis.RedisKey.MOBILE_VERIFY_CODE(mobile));
        // 3.检测该手机号是否已被占用
        cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal userAuthLocal = userAuthLocalService.getByAccount(mobile);
        if (null != userAuthLocal) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.MOBILE_REGISTERED);
        }
        // 4.查找该用户的其他本地认证信息
        java.lang.String password = getPasswordInUserAuthLocal(userId);
        // 5.绑定手机号
        userAuthLocal = new cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal();
        userAuthLocal.setUserId(userId);
        userAuthLocal.setAccount(mobile);
        userAuthLocal.setPassword(password);
        userAuthLocal.setAccountType(cn.meteor.beyondclouds.modules.user.enums.AccountType.MOBILE.getType());
        userAuthLocal.setStatus(cn.meteor.beyondclouds.modules.user.enums.AuthStatus.NORMAL.getStatus());
        userAuthLocalService.save(userAuthLocal);
    }

    private java.lang.String getPasswordInUserAuthLocal(java.lang.String userId) {
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> userAuthLocalQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        userAuthLocalQueryWrapper.eq("user_id", userId);
        java.util.List<cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal> userAuthLocalList = userAuthLocalService.list(userAuthLocalQueryWrapper);
        if (!org.springframework.util.CollectionUtils.isEmpty(userAuthLocalList)) {
            for (cn.meteor.beyondclouds.modules.user.entity.UserAuthLocal ual : userAuthLocalList) {
                if (!org.springframework.util.StringUtils.isEmpty(ual.getPassword())) {
                    return ual.getPassword();
                }
            }
        }
        return null;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.entity.UserStatistics getStatistics(java.lang.String userId, boolean updateVisted) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics = userStatisticsService.getById(userId);
        if (null == userStatistics) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_NOT_EXISTS);
        }
        if (updateVisted) {
            userStatistics.setVisitedNum(userStatistics.getVisitedNum() + 1);
            userStatisticsService.updateById(userStatistics);
        }
        return userStatistics;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO getUserInfo(java.lang.String userId, boolean updateVisited) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        cn.meteor.beyondclouds.modules.user.entity.User user = getById(userId);
        if (null == user) {
            throw new cn.meteor.beyondclouds.modules.user.exception.UserServiceException(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.USER_NOT_EXISTS);
        }
        cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics = getStatistics(userId, updateVisited);
        cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO userInfoDTO = new cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO();
        org.springframework.beans.BeanUtils.copyProperties(user, userInfoDTO);
        userInfoDTO.setStatistics(userStatistics);
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.lang.String currentUserId = ((java.lang.String) (cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId()));
            if (currentUserId.equals(userId)) {
                java.util.Date nickModifyStartTime = user.getNickModifyStartTime();
                java.lang.Integer nickModifyCount = user.getNickModifyCount();
                boolean canModifyNick;
                // 如果count等于0或nickModifyStartTime为null，则重置nickModifyStartTime为当前时间，nickModifyCount设置为1
                if ((0 == nickModifyCount) || (nickModifyStartTime == null)) {
                    canModifyNick = true;
                } else if ((java.lang.System.currentTimeMillis() - nickModifyStartTime.getTime()) > (cn.meteor.beyondclouds.modules.user.service.impl.UserServiceImpl.MILLS_OF_DAY * 30)) {
                    // 判断本月时间是否已经过去，过去则重置
                    canModifyNick = true;
                } else // 判断本月修改次数是否超过3次
                if (nickModifyCount >= 3) {
                    canModifyNick = false;
                } else {
                    canModifyNick = true;
                }
                userInfoDTO.setNickCanModify(canModifyNick);
            }
        }
        return userInfoDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getHotBloggers(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userPage = userMapper.selectHotBloggerPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize));
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOList = userPage.getRecords();
        if (!org.springframework.util.CollectionUtils.isEmpty(userFollowDTOList)) {
            if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
                java.util.Set<java.lang.String> followUserIds = userFollowService.getCurrentUserFollowedUserIds();
                userFollowDTOList.forEach(userFollowDTO -> {
                    userFollowDTO.setFollowedUser(followUserIds.contains(userFollowDTO.getUserId()));
                });
            } else {
                userFollowDTOList.forEach(userFollowDTO -> {
                    userFollowDTO.setFollowedUser(false);
                });
            }
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userPage, userFollowDTOPage);
        userFollowDTOPage.setDataList(userFollowDTOList);
        return userFollowDTOPage;
    }

    @java.lang.Override
    public java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> listByIdsWithStatistics(java.util.List<java.lang.String> userIds) {
        return userMapper.listByIdsWithStatistics(userIds);
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getActivesUsers(java.lang.Integer page, java.lang.Integer size) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = userMapper.selectActivesUserPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, size));
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOList = userFollowDTOPage.getRecords();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.util.Set<java.lang.String> followedUserIds = userFollowService.getCurrentUserFollowedUserIds();
            userFollowDTOList.stream().forEach(userFollowDTO -> {
                userFollowDTO.setFollowedUser(followedUserIds.contains(userFollowDTO.getUserId()));
            });
        } else {
            userFollowDTOList.stream().forEach(userFollowDTO -> {
                userFollowDTO.setFollowedUser(false);
            });
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userFollowDTOPage, pageDTO);
        pageDTO.setDataList(userFollowDTOList);
        return pageDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getEliteUsers(java.lang.Integer page, java.lang.Integer size) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = userMapper.selectEliteUserPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, size));
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOList = userFollowDTOPage.getRecords();
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.util.Set<java.lang.String> followedUserIds = userFollowService.getCurrentUserFollowedUserIds();
            userFollowDTOList.stream().forEach(userFollowDTO -> {
                userFollowDTO.setFollowedUser(followedUserIds.contains(userFollowDTO.getUserId()));
            });
        } else {
            userFollowDTOList.stream().forEach(userFollowDTO -> {
                userFollowDTO.setFollowedUser(false);
            });
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> pageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userFollowDTOPage, pageDTO);
        pageDTO.setDataList(userFollowDTOList);
        return pageDTO;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> getHotReplies(java.lang.Integer pageNumber, java.lang.Integer pageSize) {
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userPage = userMapper.selectHotReplierPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNumber, pageSize));
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOList = userPage.getRecords();
        if (!org.springframework.util.CollectionUtils.isEmpty(userFollowDTOList)) {
            if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
                java.util.Set<java.lang.String> followUserIds = userFollowService.getCurrentUserFollowedUserIds();
                userFollowDTOList.forEach(userFollowDTO -> {
                    userFollowDTO.setFollowedUser(followUserIds.contains(userFollowDTO.getUserId()));
                });
            } else {
                userFollowDTOList.forEach(userFollowDTO -> {
                    userFollowDTO.setFollowedUser(false);
                });
            }
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        cn.meteor.beyondclouds.util.PageUtils.copyMeta(userPage, userFollowDTOPage);
        userFollowDTOPage.setDataList(userFollowDTOList);
        return userFollowDTOPage;
    }

    @java.lang.Override
    public void logout() {
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
            tokenManager.removeTokenAndRefreshToken(java.lang.String.valueOf(subject.getId()), subject.getClientType());
            messageQueueService.sendUserActionMessage(cn.meteor.beyondclouds.core.queue.message.UserActionMessage.logoutMessage(cn.meteor.beyondclouds.util.SubjectUtils.getSubject()));
        }
    }

    private java.lang.String generateAvatarAndSave(java.lang.String id) throws java.io.IOException, cn.meteor.beyondclouds.common.exception.OssException {
        return ossHelper.upload(cn.meteor.beyondclouds.util.AvatarUtils.create(id), ("avatar/" + cn.meteor.beyondclouds.util.UUIDUtils.randomUUID()) + ".png");
    }
}