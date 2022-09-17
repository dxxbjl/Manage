package io.github.yangyouwang.crud.system.mapper;

import io.github.yangyouwang.common.base.CrudBaseMapper;
import io.github.yangyouwang.crud.system.entity.SysUser;
import io.github.yangyouwang.crud.system.model.SysUserDTO;

import java.util.List;


/**
 * @author yangyouwang
 * @title: SysUserMapper
 * @projectName crud
 * @description: 用户Mapper
 * @date 2021/3/2112:25 AM
 */
public interface SysUserMapper extends CrudBaseMapper<SysUser> {

    /**
     * 根据用户名称查询用户信息
     * @param userName 用户名称
     * @return 用户信息
     */
    SysUser findUserByName(String userName);

    /**
     * 根据用户id查询用户详情
     * @param id 用户id
     * @return 用户详情
     */
    SysUser findUserByUserId(Long id);

    /**
     * 获取全部用户
     * @return 用户列表
     */
    List<SysUserDTO> findUserList();
}
