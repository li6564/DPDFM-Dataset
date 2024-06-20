package cn.meteor.beyondclouds.modules.user.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author meteor
 */
@io.swagger.annotations.Api(tags = "用户认证Api")
@cn.meteor.beyondclouds.modules.user.api.RestController
@cn.meteor.beyondclouds.modules.user.api.RequestMapping("/api/auth")
public class AuthenticationApi {
    private cn.meteor.beyondclouds.modules.user.service.IAuthenticationService authenticationService;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthenticationApi(cn.meteor.beyondclouds.modules.user.service.IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("账号密码认证")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/password")
    public cn.meteor.beyondclouds.core.api.Response<?> localAuth(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.LocalAuthFrom localAuthFrom, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.user.api.RequestParam(value = "clientType", required = false)
    java.lang.Integer clientType) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO authenticationResult = null;
        cn.meteor.beyondclouds.modules.user.enums.ClientType type = cn.meteor.beyondclouds.modules.user.enums.ClientType.valueOf(clientType);
        try {
            authenticationResult = authenticationService.localAuthentication(localAuthFrom.getAccount(), localAuthFrom.getPassword(), type);
            return cn.meteor.beyondclouds.core.api.Response.success(authenticationResult);
        } catch (cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("短信验证登陆")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/sms")
    public cn.meteor.beyondclouds.core.api.Response smsAuth(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    @io.swagger.annotations.ApiParam("短信认证表单")
    cn.meteor.beyondclouds.modules.user.form.SmsAuthFrom smsAuthFrom, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.modules.user.api.RequestParam(value = "clientType", required = false)
    java.lang.Integer clientType) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO authenticationResult = null;
        cn.meteor.beyondclouds.modules.user.enums.ClientType type = cn.meteor.beyondclouds.modules.user.enums.ClientType.valueOf(clientType);
        try {
            authenticationResult = authenticationService.smsAuthentication(smsAuthFrom.getMobile(), smsAuthFrom.getVerifyCode(), type);
            return cn.meteor.beyondclouds.core.api.Response.success(authenticationResult);
        } catch (cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("QQ认证")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/qq")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO> qqAuth(@cn.meteor.beyondclouds.modules.user.api.RequestParam("code")
    java.lang.String code, @cn.meteor.beyondclouds.modules.user.api.RequestParam(value = "clientType", required = false)
    java.lang.Integer clientType) {
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO authenticationResult = null;
        cn.meteor.beyondclouds.modules.user.enums.ClientType type = cn.meteor.beyondclouds.modules.user.enums.ClientType.valueOf(clientType);
        try {
            authenticationResult = authenticationService.qqAuthentication(code, type);
            return cn.meteor.beyondclouds.core.api.Response.success(authenticationResult);
        } catch (cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @io.swagger.annotations.ApiOperation("刷新token")
    @cn.meteor.beyondclouds.modules.user.api.PostMapping("/refresh")
    public cn.meteor.beyondclouds.core.api.Response refreshToken(@cn.meteor.beyondclouds.modules.user.api.RequestBody
    @javax.validation.Valid
    cn.meteor.beyondclouds.modules.user.form.RefreshTokenFrom refreshTokenFrom, org.springframework.validation.BindingResult bindingResult) throws cn.meteor.beyondclouds.modules.user.exception.UserServiceException {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        cn.meteor.beyondclouds.modules.user.dto.AuthenticationResultDTO authenticationResult = null;
        try {
            authenticationResult = authenticationService.refreshToken(refreshTokenFrom.getRefreshToken());
            return cn.meteor.beyondclouds.core.api.Response.success(authenticationResult);
        } catch (cn.meteor.beyondclouds.modules.user.exception.AuthenticationServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        }
    }

    @io.swagger.annotations.ApiOperation("获取我的认证列表")
    @cn.meteor.beyondclouds.modules.user.api.GetMapping("/auths")
    public cn.meteor.beyondclouds.core.api.Response<cn.meteor.beyondclouds.modules.user.vo.UserAuthMapVO> auths(@cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        java.util.List<cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> userAuthDTOS = authenticationService.getAuths(java.lang.String.valueOf(subject.getId()));
        cn.meteor.beyondclouds.modules.user.vo.UserAuthMapVO userAuthMapVO = new cn.meteor.beyondclouds.modules.user.vo.UserAuthMapVO();
        java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.user.dto.UserAuthDTO> localAuthMap = new java.util.HashMap<>();
        userAuthDTOS.forEach(userAuthDTO -> {
            if (userAuthDTO.getAuthType().equals(cn.meteor.beyondclouds.modules.user.enums.AuthType.LOCAL.getType())) {
                if (userAuthDTO.getAccountType() == cn.meteor.beyondclouds.modules.user.enums.AccountType.MOBILE.getType()) {
                    localAuthMap.put("mobile", userAuthDTO);
                } else if (userAuthDTO.getAccountType() == cn.meteor.beyondclouds.modules.user.enums.AccountType.EMAIL.getType()) {
                    localAuthMap.put("email", userAuthDTO);
                }
            }
        });
        userAuthMapVO.setLocal(localAuthMap);
        return cn.meteor.beyondclouds.core.api.Response.success(userAuthMapVO);
    }
}