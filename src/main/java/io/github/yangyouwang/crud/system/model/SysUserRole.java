package io.github.yangyouwang.crud.system.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户关联角色
 * sys_user_role
 * @author yangyouwang
 */
@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}