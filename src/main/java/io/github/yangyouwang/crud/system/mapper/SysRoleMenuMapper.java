package io.github.yangyouwang.crud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yangyouwang.crud.system.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysRoleMenuMapper
 * @projectName crud
 * @description: 角色关联菜单Mapper
 * @date 2021/4/19:59 AM
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量插入角色关联菜单
     * @param sysRoleMenus 角色关联菜单列表
     * @return 新增或修改状态
     */
    int insertBatch(@Param("list") List<SysRoleMenu> sysRoleMenus);
}