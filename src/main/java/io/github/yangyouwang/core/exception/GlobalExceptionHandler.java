package io.github.yangyouwang.core.exception;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.enums.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangyouwang
 * @title: GlobalExceptionHandler
 * @projectName crud
 * @description: 全局异常处理
 * @date 2021/3/209:31 PM
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return Result.failure(e.getMessage());
    }


    /**
     * 处理token认证异常
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public Result exceptionHandler(UnauthorizedException e){
        log.error("发生token认证异常！原因是:",e);
        return Result.ok(ResultStatus.NO_PERMISSION);
    }

    /**
     * 处理权限的异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ModelAndView exceptionHandler(AccessDeniedException e){
        log.error("发生权限异常！原因是:",e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return Result.failure(e.getMessage());
    }
}
