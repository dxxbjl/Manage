package io.github.yangyouwang.common.annotation;

import java.lang.annotation.*;

/**
*   字典类型自定义注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DictType {
    /**
     * 获取字典类型
     * @return 字典类型
     */
    String key();
}
