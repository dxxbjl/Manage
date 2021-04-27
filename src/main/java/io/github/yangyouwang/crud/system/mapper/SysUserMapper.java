package io.github.yangyouwang.crud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.yangyouwang.crud.system.model.SysUser;

/**
 * @author yangyouwang
 * @title: SysUserMapper
 * @projectName crud
 * @description: 用户Mapper
 * @date 2021/3/2112:25 AM
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

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
}
