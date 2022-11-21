package io.github.yangyouwang.core.log.listener;

import cn.hutool.extra.servlet.ServletUtil;
import io.github.yangyouwang.core.util.StringUtil;
import io.github.yangyouwang.crud.system.entity.SysLoginLog;
import io.github.yangyouwang.crud.system.service.SysLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

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
        // 登录账号
        String username = event.getAuthentication().getPrincipal().toString();
        // 登录失败原因
        String message = StringUtil.getAuthenticationFailureMessage(event);
        // 请求IP
        String ip = ServletUtil.getClientIP(((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest(), "");
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setAccount(username);
        sysLoginLog.setLoginIp(ip);
        sysLoginLog.setRemark(message);
        sysLoginLogService.save(sysLoginLog);
    }
}
