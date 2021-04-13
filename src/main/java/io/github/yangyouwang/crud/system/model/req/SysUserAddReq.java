package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserAddReq
 * @projectName crud
 * @description: 用户请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysUserAddReq implements Serializable {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String userName;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String passWord;
    /**
     * 启用
     */
    @NotBlank(message = "启用不能为空")
    private String enabled;
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    @NotBlank(message = "用户性别不能为空")
    private String sex;
    /**
     * 头像
     */
    private String avatar;

    /** 备注 */
    private String remark;
    /**
     * 角色id
     */
    @NotEmpty(message = "角色id不能为空")
    private Long[] roleIds;
}