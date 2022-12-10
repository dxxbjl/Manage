package io.github.yangyouwang.core.util;
import io.github.yangyouwang.common.domain.ApiContext;
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

    private static final UserMapper userMapper;

    static {
        userMapper = SpringUtils.getBean(UserMapper.class);
    }
    /**
     * 获取当前登录用户名称
     * @return 用户名称
     */
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ApiContext.getUserId();
        if (Objects.nonNull(authentication)) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }
        io.github.yangyouwang.crud.app.entity.User user = userMapper.selectById(userId);
        if(Objects.nonNull((user))) {
            return user.getNickName();
        }
        return null;
    }
}
