package io.github.yangyouwang.core.util;
import io.github.yangyouwang.core.context.ApiContext;
import io.github.yangyouwang.crud.app.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * @author yangyouwang
 * @title: SecurityUtils
 * @projectName crud
 * @description: Security工具类
 * @date 2021/3/312:34 PM
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户名称
     * @return 用户名称
     */
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ApiContext.getUserId();
        if (userId == null && authentication == null) {
            return null;
        }
        if (Objects.nonNull(authentication) && authentication instanceof User) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }
        UserMapper userMapper = SpringUtils.getBean(UserMapper.class);
        return userMapper.selectById(userId).getNickName();
    }
}
