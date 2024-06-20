package cn.meteor.beyondclouds.modules.user.api;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author meteor
 */
@io.swagger.annotations.Api(tags = "用户Api")
@cn.meteor.beyondclouds.modules.user.api.RestController
@cn.meteor.beyondclouds.modules.user.api.RequestMapping("/api")
@lombok.extern.slf4j.Slf4j
public class UserApi {
    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.modules.user.service.IUserBlacklistService userBlacklistService;

    private cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApi(cn.meteor.beyondclouds.modules.user.service.IUserService userService, cn.meteor.beyondclouds.modules.user.service.IUserBlacklistService userBlacklistService, cn.meteor.beyondclouds.modules.user.service.IUserFollowService userFollowService) {
        this.userService = userService;
        this.userBlacklistService = userBlacklistService;
        this.userFollowService = userFollowService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("手机号注册")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/user/register/mobile")
    public cn.meteor.beyondclouds.core.api.Response mobileRegister(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.MobileRegisterFrom registerFrom, org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        try {
            userService.registerByMobile(registerFrom.getMobile(), registerFrom.getPassword(), registerFrom.getVerifyCode());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("邮箱注册")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/user/register/email")
    public cn.meteor.beyondclouds.core.api.Response emailRegister(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.EmailRegisterFrom registerFrom, org.springframework.validation.BindingResult result) {
        if (true) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.user.enums.UserErrorCode.EMAIL_CLOSED);
        }
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        try {
            userService.registerByEmail(registerFrom.getEmail(), registerFrom.getPassword());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("绑定邮箱")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/user/binding/email")
    public cn.meteor.beyondclouds.core.api.Response bindEmail(@javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.EmailBindingFrom emailBindingFrom, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        try {
            userService.bindEMail(emailBindingFrom.getEmail(), emailBindingFrom.getVerifyCode(), ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("绑定手机号")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/user/binding/mobile")
    public cn.meteor.beyondclouds.core.api.Response bindMobile(@javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.MobileBindingFrom mobileBindingFrom, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        try {
            userService.bindMobile(mobileBindingFrom.getMobile(), mobileBindingFrom.getVerifyCode(), ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("激活账号")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/active/{activeCode}")
    public cn.meteor.beyondclouds.core.api.Response emailRegister(@cn.meteor.beyondclouds.modules.user.api.PathVariable("activeCode")
    java.lang.String activeCode) {
        try {
            userService.activeAccount(activeCode);
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("修改我的基本信息")
    @cn.meteor.beyondclouds.modules.user.api.PutMapping("/my/baseinfo")
    public cn.meteor.beyondclouds.core.api.Response alterBaseInfo(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.UserBaseInfoFrom userBaseinfoFrom, org.springframework.validation.BindingResult result, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        // 将form转换为user对象
        cn.meteor.beyondclouds.modules.user.entity.User user = new cn.meteor.beyondclouds.modules.user.entity.User();
        org.springframework.beans.BeanUtils.copyProperties(userBaseinfoFrom, user);
        // 修改基本信息
        user.setUserId(((java.lang.String) (subject.getId())));
        userService.alterBaseInfo(user);
        return cn.meteor.beyondclouds.core.api.Response.success();
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("修改密码")
    @cn.meteor.beyondclouds.modules.user.api.PutMapping("/my/password")
    public cn.meteor.beyondclouds.core.api.Response alterPassword(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.MobileRegisterFrom registerFrom, org.springframework.validation.BindingResult result) {
        if (result.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(result.getFieldError());
        }
        try {
            userService.alterPassword(registerFrom.getMobile(), registerFrom.getPassword(), registerFrom.getVerifyCode());
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("获取我的基本信息")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/my/baseinfo")
    public cn.meteor.beyondclouds.core.api.Response myBaseInfo(@cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 获取用户信息
        try {
            cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO userInfoDTO = userService.getUserInfo(((java.lang.String) (subject.getId())), false);
            // 返回结果
            return cn.meteor.beyondclouds.core.api.Response.success(userInfoDTO);
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取他人基本信息")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/{userId}/baseinfo")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.user.vo.UserInfoWithFollowedVO> updateOtherBaseInfo(@cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(type = cn.meteor.beyondclouds.core.flow.ParamType.USER_STATISTICS, paramName = "userId")
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        boolean updateVisited = false;
        if ((null != accessInfo.getFieldVisitCount()) && (accessInfo.getFieldVisitCount() == 0)) {
            updateVisited = true;
        }
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            if (subject.getId().equals(userId)) {
                updateVisited = false;
            }
        }
        // 获取用户信息
        try {
            cn.meteor.beyondclouds.modules.user.dto.UserInfoDTO userInfoDTO = userService.getUserInfo(userId, updateVisited);
            // 去除敏感信息
            userInfoDTO.setEmail(null);
            userInfoDTO.setMobile(null);
            userInfoDTO.setQqNumber(null);
            userInfoDTO.setWxNumber(null);
            cn.meteor.beyondclouds.modules.user.vo.UserInfoWithFollowedVO userInfoWithFollowedVO = new cn.meteor.beyondclouds.modules.user.vo.UserInfoWithFollowedVO();
            org.springframework.beans.BeanUtils.copyProperties(userInfoDTO, userInfoWithFollowedVO);
            if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
                userInfoWithFollowedVO.setFollowedUser(userFollowService.hasFollowedUser(userInfoDTO.getUserId()));
            } else {
                userInfoWithFollowedVO.setFollowedUser(false);
            }
            // 返回结果
            return cn.meteor.beyondclouds.core.api.Response.success(userInfoWithFollowedVO);
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("拉黑")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/my/blacklist/{userId}")
    public cn.meteor.beyondclouds.core.api.Response blacklist(@cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            userBlacklistService.blacklist(userId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("关注")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/user/{userId}/follower")
    public cn.meteor.beyondclouds.core.api.Response follower(@cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            userFollowService.follow(userId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("取消拉黑")
    @cn.meteor.beyondclouds.modules.user.api.DeleteMapping("/my/blacklist/{userId}")
    public cn.meteor.beyondclouds.core.api.Response deBlack(@cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            userBlacklistService.deBlack(userId, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("我的黑名单")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/my/blacklist/{userId}")
    public cn.meteor.beyondclouds.core.api.Response myBlacklist(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId) {
        // 根据userId获取粉丝并返回
        com.baomidou.mybatisplus.core.metadata.IPage<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> blacklistIPage = userBlacklistService.getMyBlackList(pageForm.getPage(), pageForm.getSize(), userId);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.entity.UserBlacklist> blacklistPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(blacklistIPage);
        return cn.meteor.beyondclouds.core.api.Response.success(blacklistPageDTO);
    }

    @io.swagger.annotations.ApiOperation("取消关注")
    @cn.meteor.beyondclouds.modules.user.api.DeleteMapping("/user/{userId}/follower")
    public cn.meteor.beyondclouds.core.api.Response delFollower(@cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        try {
            userFollowService.delFollow(userId, java.lang.String.valueOf(subject.getId()));
            return cn.meteor.beyondclouds.core.api.Response.success();
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @io.swagger.annotations.ApiOperation("获取我的粉丝")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/my/fans")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO>> myFans(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 根据userId获取粉丝并返回
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> fansPage = userFollowService.getFansPage(pageForm.getPage(), pageForm.getSize(), ((java.lang.String) (subject.getId())));
        return cn.meteor.beyondclouds.core.api.Response.success(fansPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取他人粉丝")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/{userId}/fans")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO>> otherFans(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId) {
        // 根据userId获取粉丝并返回
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> fansPage = userFollowService.getFansPage(pageForm.getPage(), pageForm.getSize(), userId);
        return cn.meteor.beyondclouds.core.api.Response.success(fansPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @io.swagger.annotations.ApiOperation("获取我的关注")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/my/follower")
    public cn.meteor.beyondclouds.core.api.Response myFollower(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 根据userId获取关注列表并返回
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> followedPage = userFollowService.getFollowedPage(pageForm.getPage(), pageForm.getSize(), java.lang.String.valueOf(subject.getId()));
        return cn.meteor.beyondclouds.core.api.Response.success(followedPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取他人关注")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/{userId}/follower")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO>> otherFollower(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, @cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId) {
        // 根据userId获取关注列表并返回
        cn.meteor.beyondclouds.common.dto.PageDTO followedPage = userFollowService.getFollowedPage(pageForm.getPage(), pageForm.getSize(), userId);
        return cn.meteor.beyondclouds.core.api.Response.success(followedPage);
    }

    @io.swagger.annotations.ApiOperation("获取我的统计信息")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/my/statistics")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> myStatistics(@cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 获取用户信息
        cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics;
        try {
            userStatistics = userService.getStatistics(java.lang.String.valueOf(subject.getId()), false);
            // 返回结果
            return cn.meteor.beyondclouds.core.api.Response.success(userStatistics);
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("获取他人统计信息")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/{userId}/statistics")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.user.entity.UserStatistics> othersStatistics(@cn.meteor.beyondclouds.modules.user.api.PathVariable(name = "userId")
    java.lang.String userId, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(type = cn.meteor.beyondclouds.core.flow.ParamType.USER_STATISTICS, paramName = "userId")
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        // 获取用户信息
        cn.meteor.beyondclouds.modules.user.entity.UserStatistics userStatistics;
        try {
            userStatistics = userService.getStatistics(userId, false);
            // 返回结果
            return cn.meteor.beyondclouds.core.api.Response.success(userStatistics);
        } catch (cn.meteor.beyondclouds.modules.user.exception.UserServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门博主")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/hotBloggers")
    public cn.meteor.beyondclouds.core.api.Response<?> hotBloggers(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = userService.getHotBloggers(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(userFollowDTOPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("热门答主")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/hotRepliers")
    public cn.meteor.beyondclouds.core.api.Response<?> hotRepliers(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> userFollowDTOPage = userService.getHotReplies(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(userFollowDTOPage);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("社区精英")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/elites")
    public cn.meteor.beyondclouds.core.api.Response<?> getEliteUsers(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> pageDTO = userService.getEliteUsers(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks
    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("活跃用户")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/actives")
    public cn.meteor.beyondclouds.core.api.Response<?> getActiveUsers(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.user.dto.UserFollowDTO> pageDTO = userService.getActivesUsers(pageForm.getPage(), pageForm.getSize());
        return cn.meteor.beyondclouds.core.api.Response.success(pageDTO);
    }

    @io.swagger.annotations.ApiOperation("注销登录")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/logout")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.user.vo.UserAuthMapVO> logout() {
        userService.logout();
        return cn.meteor.beyondclouds.core.api.Response.success();
    }

    @io.swagger.annotations.ApiOperation("保活心跳接口")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/user/keepAlive")
    public cn.meteor.beyondclouds.core.api.Response<?> keepAlive() {
        log.info("收到{}的心跳消息！", cn.meteor.beyondclouds.util.SubjectUtils.getSubject());
        return cn.meteor.beyondclouds.core.api.Response.success();
    }
}