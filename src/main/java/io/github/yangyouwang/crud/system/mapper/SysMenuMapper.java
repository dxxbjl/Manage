package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.common.base.CrudBaseMapper;
import io.github.yangyouwang.common.domain.TreeSelectNode;
import io.github.yangyouwang.crud.system.entity.SysMenu;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysMenuMapper
 * @projectName crud
 * @description: 菜单Mapper
 * @date 2021/3/2112:25 AM
 */
public interface SysMenuMapper extends CrudBaseMapper<SysMenu> {

    /**
     * 根据菜单类型和状态查询菜单
     * @return 菜单列表
     */
    List<SysMenu> findMenu();
    /**
     * 根据用户id查询菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    List<SysMenu> findMenuByUserId(Long userId);
    /**
     * 根据角色查询菜单权限
     * @param roleId 角色
     * @return 权限列表
     */
    List<String> findMenuRoleByRoleId(Long roleId);

    /**
     * 查询所有菜单权限
     * @return 权限列表
     */
    List<String> findMenuRole();
    /**
     * 查询菜单树结构
     * @return 菜单树结构
     */
    List<TreeSelectNode> getMenuTree();
}
