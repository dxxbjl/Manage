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
    ERROR(500, "Server Error"),
    NO_PERMISSION(401,"No Permission");

    /** 业务异常码 */
    public Integer CODE;
    /** 业务异常信息描述 */
    public String MESSAGE;

    ResultStatus(Integer CODE, String MESSAGE) {
        this.CODE = CODE;
        this.MESSAGE = MESSAGE;
    }
}