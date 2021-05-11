package io.github.yangyouwang.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.yangyouwang.common.enums.ResultStatus;
import io.github.yangyouwang.core.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author yangyouwang
 * @title: MetaObjectHandlerConfig
 * @projectName crud
 * @description: 自动填充配置
 * @date 2021/3/2311:17 AM
 */
@Configuration
@Slf4j
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        User sysUser = SecurityUtils.getSysUser();
        Assert.notNull(sysUser, ResultStatus.NO_LOGIN.message);
        this.setFieldValByName("createBy",sysUser.getUsername(),metaObject);
        this.setFieldValByName("createTime",new Date(),metaObject);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        User sysUser = SecurityUtils.getSysUser();
        Assert.notNull(sysUser, ResultStatus.NO_LOGIN.message);
        this.setFieldValByName("updateBy", sysUser.getUsername(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}