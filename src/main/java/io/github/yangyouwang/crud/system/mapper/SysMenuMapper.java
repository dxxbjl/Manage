package io.github.yangyouwang.crud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yangyouwang.crud.system.model.SysMenu;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysMenuMapper
 * @projectName crud
 * @description: 菜单Mapper
 * @date 2021/3/2112:25 AM
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据菜单类型和状态查询菜单
     * @return 菜单列表
     */
    List<SysMenu> findSysMenu();

    /**
     * 根据用户id查询菜单
     * @param id 主键
     * @return 菜单列表
     */
    List<SysMenu> findSysMenuByName(String name);

    /**
     * 根据id查询菜单
     * @param id 主键
     * @return 菜单详情
     */
    SysMenu findSysMenuById(Long id);

    /**
     * 根据角色查询菜单
     * @param roleId 角色
     * @return 菜单列表
     */
    List<SysMenu> findMenuByRoleId(Long roleId);
}
