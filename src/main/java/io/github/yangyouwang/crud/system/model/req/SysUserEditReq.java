package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserEditReq
 * @projectName crud
 * @description: 用户编辑
 * @date 2021/3/269:56 AM
 */
@Data
public class SysUserEditReq implements Serializable {

    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 启用
     */
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
    private Long[] roleIds;
}
