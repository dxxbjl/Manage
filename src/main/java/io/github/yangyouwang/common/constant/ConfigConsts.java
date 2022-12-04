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
