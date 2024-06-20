package cn.meteor.beyondclouds.common.helper.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
/**
 *
 * @author 段启岩
 */
@org.springframework.stereotype.Component
public class QQAuthenticationHelperImpl implements cn.meteor.beyondclouds.common.helper.IQQAuthenticationHelper {
    private cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties beyondCloudsProperties;

    @org.springframework.beans.factory.annotation.Autowired
    public QQAuthenticationHelperImpl(cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties beyondCloudsProperties) {
        this.beyondCloudsProperties = beyondCloudsProperties;
    }

    @java.lang.Override
    public cn.meteor.beyondclouds.common.dto.QQAuthResultDTO authentication(java.lang.String code) throws cn.meteor.beyondclouds.common.exception.QQAuthenticationException {
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        cn.meteor.beyondclouds.config.properties.BeyondCloudsProperties.QQAuthProperties qqAuthProperties = beyondCloudsProperties.getAuth().getQq();
        // 1.向QQ的认证服务器发送请求，换取accessToken
        java.lang.String tokenUri = (((((("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + qqAuthProperties.getClientId()) + "&client_secret=") + qqAuthProperties.getClientSecret()) + "&code=") + code) + "&redirect_uri=") + qqAuthProperties.getRedirectUri();
        // 发起请求
        java.lang.String response = restTemplate.getForObject(tokenUri, java.lang.String.class);
        int indexOfAccessToken = response.indexOf("access_token");
        if ((-1) == indexOfAccessToken) {
            throw new cn.meteor.beyondclouds.common.exception.QQAuthenticationException("QQ认证-token获取失败");
        }
        // 解析accessToken
        java.lang.String accessToken = response.substring(response.indexOf("=") + 1, response.indexOf("&"));
        // 2.使用accessToken获取QQ用户信息
        java.lang.String openIdUri = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
        response = restTemplate.getForObject(openIdUri, java.lang.String.class);
        int indexOfOpenId = response.indexOf("openid");
        if ((-1) == indexOfOpenId) {
            throw new cn.meteor.beyondclouds.common.exception.QQAuthenticationException("QQ认证-openid获取失败");
        }
        // 解析openid
        java.lang.String openId = response.substring(response.lastIndexOf(":") + 2, response.lastIndexOf("\""));
        // 3.使用accessToken和openId获取用户信息
        java.lang.String userInfoUri = (((("https://graph.qq.com/user/get_user_info?access_token=" + accessToken) + "&oauth_consumer_key=") + qqAuthProperties.getClientId()) + "&openid=") + openId;
        response = restTemplate.getForObject(userInfoUri, java.lang.String.class);
        java.util.Map userInfoMap = cn.meteor.beyondclouds.util.JsonUtils.toBean(response, java.util.Map.class);
        if (0 != ((java.lang.Integer) (userInfoMap.get("ret")))) {
            throw new cn.meteor.beyondclouds.common.exception.QQAuthenticationException("QQ认证-user_info获取失败");
        }
        cn.meteor.beyondclouds.common.dto.QQAuthResultDTO qqAuthResult = new cn.meteor.beyondclouds.common.dto.QQAuthResultDTO();
        qqAuthResult.setAccessToken(accessToken);
        qqAuthResult.setOpenId(openId);
        java.lang.String genderStr = ((java.lang.String) (userInfoMap.get("gender")));
        int gender = 0;
        if ("男".equals(genderStr)) {
            gender = 1;
        } else if ("女".equals(genderStr)) {
            gender = 2;
        }
        qqAuthResult.setGender(gender);
        qqAuthResult.setNickName(((java.lang.String) (userInfoMap.get("nickname"))));
        qqAuthResult.setAvatar(((java.lang.String) (userInfoMap.get("figureurl_2"))));
        return qqAuthResult;
    }
}