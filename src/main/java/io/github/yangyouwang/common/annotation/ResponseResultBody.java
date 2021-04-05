package io.github.yangyouwang.common.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @author yangyouwang
 * @title: ResponseResultBody
 * @projectName crud
 * @description: 全局处理返回值
 * @date 2021/3/209:17 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {

    String value() default "";
}
