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
     * 用户SheetName
     */
    String SYS_USER_SHEET_NAME = "用户信息";
    /**
     * 字典性别key
     */
    String DICT_KEY_SEX = "sex";
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
     * 可用状态
     */
    int USER_STATUS_AVAILABLE = 0;
}
