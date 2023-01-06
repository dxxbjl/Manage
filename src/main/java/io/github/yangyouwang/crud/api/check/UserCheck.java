package io.github.yangyouwang.crud.api.check;

import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.core.util.SpringUtils;
import io.github.yangyouwang.crud.app.entity.User;
import io.github.yangyouwang.crud.app.mapper.UserMapper;
import org.springframework.util.Assert;

/**
 * 用户检查类
 */
public class UserCheck {

    private static UserMapper userMapper;

    static {
        userMapper = SpringUtils.getBean(UserMapper.class);
    }
    /**
     * 检查用户是否冻结
     * @param userId 用户ID
     */
    public static void checkUserStatus(Long userId) {
        User user = userMapper.selectById(userId);
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(ConfigConsts.USER_STATUS_AVAILABLE == user.getStatus().intValue(),"该用户已冻结");
    }
}
