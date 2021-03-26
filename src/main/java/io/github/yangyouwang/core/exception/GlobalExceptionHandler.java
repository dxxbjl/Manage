package io.github.yangyouwang.core.exception;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.enums.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author yangyouwang
 * @title: GlobalExceptionHandler
 * @projectName crud
 * @description: 全局异常处理
 * @date 2021/3/209:31 PM
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return Result.ok(ResultStatus.ERROR.code, e.getMessage(), null);
    }

    /**
     * 处理权限的异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result exceptionHandler(AccessDeniedException e){
        log.error("发生权限异常！原因是:",e);
        return Result.ok(ResultStatus.ERROR.code, e.getMessage(), null);
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    public Result exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return Result.ok(ResultStatus.ERROR.getCode(), e.getMessage(), null);
    }
}
