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
     * 微信小程序获取openid api
     */
    String WEIXIN_OPENID_API = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    /**
     * 登录类型 wx 微信
     */
    String WX_APP_TYPE = "wx";
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
}
