package io.github.yangyouwang.system.dao;

import io.github.yangyouwang.system.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangyouwang
 * @title: SysRoleRepository
 * @projectName crud
 * @description: 角色Dao接口
 * @date 2021/3/2112:25 AM
 */
public interface SysRoleRepository extends JpaRepository<SysRole,Integer> {

}
