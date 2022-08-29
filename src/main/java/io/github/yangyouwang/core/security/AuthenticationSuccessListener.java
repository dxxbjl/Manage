package io.github.yangyouwang.core.security;

import io.github.yangyouwang.crud.system.service.SysLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Description: 用户登录成功监听器事件<br/>
 * date: 2022/8/29 21:01<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Slf4j
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        System.out.println("success");
    }
}
