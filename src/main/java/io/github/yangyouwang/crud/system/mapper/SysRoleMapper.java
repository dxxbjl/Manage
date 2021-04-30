package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.common.base.CrudBaseMapper;
import io.github.yangyouwang.crud.system.model.SysRole;

/**
 * @author yangyouwang
 * @title: SysRoleMapper
 * @projectName crud
 * @description: 角色Dao接口
 * @date 2021/3/2112:25 AM
 */
public interface SysRoleMapper extends CrudBaseMapper<SysRole> {

    /**
     * 查询角色
     * @param id 主键
     * @return 角色
     */
    SysRole findRoleById(Long id);
}
