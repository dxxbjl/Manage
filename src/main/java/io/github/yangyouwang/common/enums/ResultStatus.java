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
    /**
     * 成功响应
     */
    SUCCESS(200, "OK"),
    /**
     * 失败响应
     */
    ERROR(500, "Server Error"),
     /**
     * 请求幂等参数不存在
     */
    IDEMPOTENT_NOT_EXIST(301,"请求幂等参数不存在"),
    /**
     * 请求非幂等
     */
    NON_IDEMPOTENT(302,"请求非幂等"),
    /**
     * 无权限响应
     */
    NO_PERMISSION(401,"无权限"),
    /**
     * 新增数据出错
     */
    SAVE_DATA_ERROR(501,"新增数据出错"),
    /**
     * 修改数据出错
     */
    UPDATE_DATA_ERROR(502,"修改数据出错"),
    /**
     * 删除数据出错
     */
    DELETE_DATA_ERROR(503,"删除数据出错"),
    /**
     * 当前层级存在下级节点
     */
    DATA_EXIST_ERROR(504,"当前层级存在下级节点"),
    /**
     * 验证码不能为空
     */
    VALIDATE_CODE_NULL_ERROR(505,"验证码不能为空"),
    /**
     * 验证码不存在
     */
    VALIDATE_CODE_NOT_EXIST_ERROR(506,"验证码不存在"),
    /**
     * 验证码不匹配
     */
    VALIDATE_CODE_NO_MATCH_ERROR(507,"验证码不匹配");

    /**
     * 业务异常码
     * */
    public Integer code;
    /**
     * 业务异常信息描述
     *  */
    public String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}