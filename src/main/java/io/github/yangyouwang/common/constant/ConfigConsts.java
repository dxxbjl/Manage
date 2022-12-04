package io.github.yangyouwang.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 通用常量
 * @author yangyouwang
 */
public interface ConfigConsts {
    /**
     * 验证码存session
     */
    String IMAGE_CODE_SESSION = "IMAGE_CODE";
    /**
     * 超级管理员
     */
    String ADMIN_USER = "admin";
    /**
     * 是否启用 是
     */
    String ENABLED_YES = "Y";
    /**
     * 字典性别key
     */
    String DICT_KEY_SEX = "sex";
    /**
     * 字典状态key
     */
    String DICT_KEY_ENABLED= "enabled";
    /**
     * 上传文件类型
     */
    List<String> IMG_TYPE = Arrays.asList("PNG","JPG","JPEG","BMP","GIF","SVG");
    /**
     * 微信小程序获取openid
     */
    String WEIXIN_OPENID_API = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    /**
     * QQ获取AuthorizationCode
     */
    String QQ_CODE_API = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=CLIENTID&redirect_uri=REDIRECTURI&state=ok";
    /**
     * QQ授权
     */
    String QQ_AUTH_API = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=CLIENTID&client_secret=CLIENTSECRET&code=CODE&redirect_uri=REDIRECTURI";
    /**
     * QQ获取openid
     */
    String QQ_OPENID_API = "https://graph.qq.com/oauth2.0/me?access_token=ACCESSTOKEN";
    /**
     * QQ获取用户信息
     */
    String QQ_GET_USER_INFO_API = "https://graph.qq.com/user/get_user_info?access_token=ACCESSTOKEN&oauth_consumer_key=APPID&openid=OPENID";
    /**
     * 登录类型 微信
     */
    String WX_APP_TYPE = "wx";
    /**
     * 登录类型 qq
     */
    String QQ_PC_TYPE = "qq";
    /**
     * 登录类型 password 账号密码
     */
    String PASSWORD_APP_TYPE = "password";
    /**
     * 登录类型 phone 手机号验证码
     */
    String PHONE_APP_TYPE = "phone";
    /**
     * 可用状态
     */
    int USER_STATUS_AVAILABLE = 0;
    /**
     * 无效
     */
    int USABLE_INVALID = 1;
    /**
     * 有效
     */
    int USABLE_EFFECTIVE = 2;
    /**
     * 已发送
     */
    int SEND_HAS_BEEN_SENT = 2;
    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";
    /**
     * 正常
     */
    String SUCCESS_STATUS = "0";
    /**
     * 失败
     */
    String ERROR_STATUS = "1";
    /**
     * 任务属性
     */
    String TASK_PROPERTIES = "task";
}
