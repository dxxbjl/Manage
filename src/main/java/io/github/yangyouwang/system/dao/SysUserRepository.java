package io.github.yangyouwang.system.dao;

import io.github.yangyouwang.system.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author yangyouwang
 * @title: SysUserRepository
 * @projectName crud
 * @description: 用户Dao接口
 * @date 2021/3/2112:25 AM
 */
public interface SysUserRepository extends JpaRepository<SysUser,Integer> {

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    @Query(value = "from SysUser where userName = ?1")
    SysUser findUserByLoginName(String userName);
}
