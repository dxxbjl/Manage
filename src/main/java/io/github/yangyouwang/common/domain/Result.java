package io.github.yangyouwang.common.domain;

import io.github.yangyouwang.common.enums.ResultStatus;
import lombok.Getter;
import lombok.ToString;

/**
 * @author yangyouwang
 * @title: Result
 * @projectName crud
 * @description: 返回体类
 * @date 2021/3/209:12 PM
 */
@Getter
@ToString
public class Result<T> {

    /** 业务错误码 */
    private Integer code;
    /** 信息描述 */
    private String message;
    /** 返回参数 */
    private T data;

    private Result(ResultStatus resultStatus, T data) {
        this.code = resultStatus.CODE;
        this.message = resultStatus.MESSAGE;
        this.data = data;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功消息
     * @return 成功消息
     */
    public static <T> Result<T> success() {
        return Result.success(ResultStatus.SUCCESS.MESSAGE);
    }

    /**
     * 返回成功消息
     * @param message 内容
     * @return 成功消息
     */
    public static <T> Result<T> success(String message) {
        return Result.success(message, null);
    }

    /**
     * 返回成功消息
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> Result<T> success(T data) {
        return Result.success(ResultStatus.SUCCESS.MESSAGE,data);
    }

    /**
     * 返回成功消息
     * @param message 内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> Result<T> success(String message,T data) {
        return new Result<>(ResultStatus.SUCCESS.CODE, message ,data);
    }

    /**
     * 返回错误消息
     * @return 错误消息
     */
    public static <T> Result<T> failure() {
        return Result.failure(ResultStatus.ERROR.MESSAGE);
    }

    /**
     * 返回错误消息
     * @param message 内容
     * @return 错误消息
     */
    public static <T> Result<T> failure(String message) {
        return Result.failure(message,null);
    }

    /**
     * 返回错误消息
     * @param data 数据对象
     * @return 错误消息
     */
    public static <T> Result<T> failure(T data) {
        return Result.failure(ResultStatus.ERROR.MESSAGE, data);
    }


    /**
     * 返回错误消息
     * @param message 内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static <T> Result<T> failure(String message,T data) {
        return new Result<>(ResultStatus.ERROR.CODE, message,data);
    }

    /***
     * 返回自定义状态
     * @param resultStatus 状态枚举
     * @return 消息
     */
    public static <T> Result<T> ok(ResultStatus resultStatus) {
        return new Result<>(resultStatus, null);
    }

    /***
     * 返回自定义状态
     * @param resultStatus 状态枚举
     * @param data 数据对象
     * @return 消息
     */
    public static <T> Result<T> ok(ResultStatus resultStatus,T data) {
        return new Result<>(resultStatus, data);
    }
}