package io.github.yangyouwang.core.config;

import io.github.yangyouwang.core.util.SecurityUtils;
import io.github.yangyouwang.system.model.SysUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * @author yangyouwang
 * @title: CreateByAuditorAware
 * @projectName crud
 * @description: 创建人和修改人jpa处理
 * @date 2021/3/2311:17 AM
 */
@Configuration
@EnableJpaAuditing
public class CreateByAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //项目中获取登录用户id的方法
        SysUser sysUser = SecurityUtils.getSysUser();
        if(sysUser != null){
            return Optional.of(sysUser.getUserName());
        }
        return Optional.empty();
    }
}