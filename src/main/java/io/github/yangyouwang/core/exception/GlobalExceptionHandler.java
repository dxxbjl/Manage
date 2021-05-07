package io.github.yangyouwang.core.exception;

import io.github.yangyouwang.common.domain.Result;
import io.github.yangyouwang.common.enums.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

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


    /**
     * 参数校验错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("参数校验错误{}", e.getBindingResult());
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage() + ";");
        }
        return Result.failure(sb.toString());
    }

    /**
     * 参数校验错误
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result handlerConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验错误{}", e.getLocalizedMessage());
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return Result.failure(message);
    }

    /**
     * 抛出框架异常
     */
    @ExceptionHandler(value = CrudException.class)
    @ResponseBody
    public Result exceptionHandler(CrudException e){
        log.error("发生框架异常！原因是:",e);
        return Result.failure(e.getLocalizedMessage());
    }
}