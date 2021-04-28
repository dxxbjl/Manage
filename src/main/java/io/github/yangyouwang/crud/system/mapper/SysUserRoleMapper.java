package io.github.yangyouwang.crud.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yangyouwang.crud.system.model.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysUserRoleMapper
 * @projectName crud
 * @description: 用户关联角色Mapper
 * @date 2021/4/19:59 AM
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量插入用户关联角色
     * @param sysUserRoles 用户关联角色列表
     * @return 新增或修改状态
     */
    int insertBatch(@Param("list") List<SysUserRole> sysUserRoles);
}