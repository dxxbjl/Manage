package io.github.yangyouwang.common.enums;


/**
 * Description: app授权类型<br/>
 * date: 2022/12/5 0:25<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
public enum AppOauthType {
    /**
     * 微信小程序登录
     */
    MP_WX,
    /**
     * 微信APP登录
     */
    WX_APP,
    /**
     * QQ登录
     */
    QQ,
    /**
     * 密码登录
     */
    PASSWORD,
    /**
     * 手机号登录
     */
    PHONE;
}
