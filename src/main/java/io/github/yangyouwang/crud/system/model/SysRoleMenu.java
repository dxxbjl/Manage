package io.github.yangyouwang.crud.system.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色菜单中间表
 * sys_role_menu
 * @author yangyouwang
 */
@Data
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}