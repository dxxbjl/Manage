package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysRole
 * @projectName crud
 * @description: 角色类
 * @date 2021/3/2112:22 AM
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = -8932638904580917498L;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
    /**
     * 多个角色对应多个菜单
     */
    @TableField(exist = false)
    private List<SysMenu> menus;
}
