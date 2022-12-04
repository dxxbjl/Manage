package io.github.yangyouwang.common.constant;

/**
 * Description: API接口常量<br/>
 * date: 2022/12/5 1:10<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
public interface ApiConsts {
    /**
     * 获取微信小程序中openid
     */
    String WX_OPENID_API = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    /**
     * 微信授权
     */
    String WX_AUTH_API = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * 获取微信用户信息
     */
    String WX_GET_USER_INFO_API = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESSTOKEN&openid=OPENID&lang=zh_CN";
    /**
     * 获取QQ中授权码
     */
    String QQ_CODE_API = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=CLIENTID&redirect_uri=REDIRECTURI&state=ok";
    /**
     * QQ授权
     */
    String QQ_AUTH_API = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=CLIENTID&client_secret=CLIENTSECRET&code=CODE&redirect_uri=REDIRECTURI";
    /**
     * 获取QQ中openid
     */
    String QQ_OPENID_API = "https://graph.qq.com/oauth2.0/me?access_token=ACCESSTOKEN";
    /**
     * 获取QQ用户信息
     */
    String QQ_GET_USER_INFO_API = "https://graph.qq.com/user/get_user_info?access_token=ACCESSTOKEN&oauth_consumer_key=APPID&openid=OPENID";
}
