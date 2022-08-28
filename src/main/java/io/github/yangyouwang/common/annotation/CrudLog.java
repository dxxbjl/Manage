package io.github.yangyouwang.common.annotation;

import io.github.yangyouwang.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @author yangyouwang
 * @title: CrudLog
 * @projectName crud
 * @description: 定义系统日志注解
 * @date 2021/4/18:40 AM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CrudLog {
    /**
     * 描述
     */
    String title();

    /**
     * 业务类型
     */
    BusinessType businessType();
}
