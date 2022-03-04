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
     * 未登录
     */
    NO_LOGIN(402,"未登录"),
    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(403,"用户名或密码错误"),

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
     * 验证码不能为空
     */
    VALIDATE_CODE_NULL_ERROR(601,"验证码不能为空"),
    /**
     * 验证码不存在
     */
    VALIDATE_CODE_NOT_EXIST_ERROR(602,"验证码不存在"),
    /**
     * 验证码不匹配
     */
    VALIDATE_CODE_NO_MATCH_ERROR(603,"验证码不匹配"),

    /**
     * 字典项不允许为空
     */
    DICT_ITEM_NULL_ERROR(701,"字典项不允许为空"),
    /**
     * 字典已存在
     */
    DICT_EXIST_ERROR(702,"字典已存在"),

    /**
     * 菜单存在子节点
     */
    MENU_EXIST_ERROR(801,"菜单存在子节点"),
    /**
     * 暂未分配菜单
     */
    MENU_NULL_ERROR(802,"暂未分配菜单"),

    /**
     * 角色已存在
     */
    ROLE_EXIST_ERROR(901,"角色已存在"),

    /**
     * 用户已存在
     */
    USER_EXIST_ERROR(1001,"用户已存在"),
    /**
     * 旧密码输入错误
     */
    OLD_PASSWORD_ERROR(1002,"旧密码输入错误");

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