package io.github.yangyouwang.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
/**
 * 角色菜单中间表
 * sys_role_menu
 * @author yangyouwang
 */
@Data
@TableName("sys_role_menu")
@ApiModel(value="SysRoleMenu对象", description="角色菜单中间表")
public class SysRoleMenu {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}
