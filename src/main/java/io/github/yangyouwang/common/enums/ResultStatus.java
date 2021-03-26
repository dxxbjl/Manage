package io.github.yangyouwang.common.enums;

import lombok.Getter;
import lombok.ToString;
/**
 * @author yangyouwang
 * @title: ResultStatus
 * @projectName crud
 * @description: 状态码枚举类
 * @date 2021/3/209:06 PM
 */
@ToString
@Getter
public enum ResultStatus {

    SUCCESS(200, "OK"),
    ERROR(500, "Server Error");

    /** 业务异常码 */
    public Integer code;
    /** 业务异常信息描述 */
    public String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}