package io.github.yangyouwang.common.constant;

/**
 * 通用常量
 * @author yangyouwang
 */
public interface ConfigConsts {
    /**
     * 登陆接口
     */
    String DEFAULT_LOGIN_URL = "/login";
    /**
     * 退出接口
     */
    String DEFAULT_LOGOUT_URL = "/logout";
    /**
     * 登陆页面
     */
    String DEFAULT_LOGIN_PAGE = "/loginPage";
    /**
     * 验证码接口
     */
    String IMG_CODE_URL = "/getImgCode";
    /**
     * 记住我 有效时间秒
     */
    int REMEMBERME_VALIDITY = 3600 * 24 * 7;
    /**
     * 记住我 cookies
     */
    String REMEMBERME_COOKIES = "JSESSIONID";
    /**
     * 验证码存session
     */
    String IMAGE_CODE_SESSION = "IMAGE_CODE";
    /**
     * 角色权限前缀
     */
    String ROLE_PREFIX = "ROLE_";
    /**
     * 超级管理员
     */
    Long ADMIN_USER = 1L;
    /**
     * 是否启用 是
     */
    String ENABLED_YES = "Y";
    /**
     * 是否启用 否
     */
    String ENABLED_NO = "N";
    /**
     * 用户SheetName
     */
    String SYS_USER_SHEET_NAME = "用户信息";
    /**
     * 字典性别key
     */
    String DICT_KEY_SEX = "sex";

    /**
     * 逻辑删除 0 否
     */
    int DELETED_NO = 0;
    /**
     * 逻辑删除 1 是
     */
    int DELETED_YES = 1;
}
