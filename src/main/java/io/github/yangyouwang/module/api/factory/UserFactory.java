package io.github.yangyouwang.module.api.factory;

import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.common.enums.AppOauthType;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.module.app.entity.Oauth;
import io.github.yangyouwang.module.app.entity.User;

/**
 * Description: 用户静态工厂<br/>
 * date: 2022/12/5 0:17<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
public class UserFactory {
    /**
     * 创建用户对象
     * @param mobile 手机号
     * @return 用户Bean
     */
    public static User createUser(String mobile) {
        User user = new User();
        String nickName = StringUtil.randomName(true,3);
        user.setNickName(nickName);
        user.setMobile(mobile);
        user.setStatus(ConfigConsts.USER_STATUS_AVAILABLE);
        return user;
    }
    /**
     * 创建用户对象
     * @param avatar 头像
     * @param nickname 昵称
     * @param gender 性别
     * @return 用户Bean
     */
    public static User createUser(String avatar,String nickname,Integer gender) {
        User user = new User();
        user.setAvatar(avatar);
        user.setNickName(nickname);
        user.setGender(gender);
        user.setStatus(ConfigConsts.USER_STATUS_AVAILABLE);
        return user;
    }
    /**
     * 创建用户授权对象
     * @param userId 用户ID
     * @param openId openId
     * @param appOauthType app授权类型
     * @return 授权对象
     */
    public static Oauth createOauth(Long userId, String openId, AppOauthType appOauthType) {
        Oauth oauth = new Oauth();
        oauth.setUserId(userId);
        oauth.setAppSecret(openId);
        oauth.setAppType(appOauthType.name());
        return oauth;
    }
}
