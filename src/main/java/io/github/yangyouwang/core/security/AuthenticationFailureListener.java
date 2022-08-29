package io.github.yangyouwang.core.security;

import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.system.service.SysLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

/**
 * Description: 用户登录失败监听器事件<br/>
 * date: 2022/8/29 20:51<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Slf4j
@Component
public class AuthenticationFailureListener  implements ApplicationListener<AbstractAuthenticationFailureEvent>  {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        String message = StringUtil.getAuthenticationFailureMessage(event);
        System.out.println(message);
    }
}
