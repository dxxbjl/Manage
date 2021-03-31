package io.github.yangyouwang.core.util;

import io.github.yangyouwang.system.model.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * @author yangyouwang
 * @title: SecurityUtils
 * @projectName crud
 * @description: Security工具类
 * @date 2021/3/312:34 PM
 */
public class SecurityUtils {

    /**
     * 获取用户信息
     * @return 用户信息
     */
    public static final SysUser getSysUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Assert.notNull(object,"该用户未登陆");
        if (object instanceof SysUser) {
            return (SysUser) object;
        }
        return null;
    }
}
