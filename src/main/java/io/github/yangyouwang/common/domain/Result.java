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

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    /** 业务成功返回业务代码和描述信息 */
    public static <T> Result<T> success() {
        return new Result<>(ResultStatus.SUCCESS, null);
    }

    /** 业务异常返回业务代码和描述信息 */
    public static <T> Result<T> failure() {
        return new Result<>(ResultStatus.ERROR, null);
    }

    /** 业务成功返回业务代码和描述信息 */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultStatus.SUCCESS, data);
    }

    /** 业务异常返回业务代码和描述信息 */
    public static <T> Result<T> failure(T data) {
        return new Result<>(ResultStatus.ERROR, data);
    }

    /** 返回自定义状态 */
    public static <T> Result<T> ok(ResultStatus resultStatus) {
        return new Result<>(resultStatus, null);
    }

    /** 返回自定义状态、数据 */
    public static <T> Result<T> ok(ResultStatus resultStatus, T data) {
        return new Result<>(resultStatus, data);
    }

    /** 返回自定义状态、消息、数据 */
    public static <T> Result<T> ok(Integer code, String message, T data) {
        return new Result<>(code, message ,data);
    }
}