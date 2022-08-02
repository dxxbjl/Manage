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
     * 上传文件类型
     */
    List<String> IMG_TYPE = Arrays.asList("PNG","JPG","JPEG","BMP","GIF","SVG");
}
