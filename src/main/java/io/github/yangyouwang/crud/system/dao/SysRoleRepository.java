package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysRole;
import io.github.yangyouwang.crud.system.model.req.SysRoleListReq;
import io.github.yangyouwang.crud.system.model.resp.SysRoleResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author yangyouwang
 * @title: SysRoleRepository
 * @projectName crud
 * @description: 角色Dao接口
 * @date 2021/3/2112:25 AM
 */
public interface SysRoleRepository extends JpaRepository<SysRole,Long> {

    /**
     * 列表请求
     * @return 请求列表
     */
    @Query(value = "select new io.github.yangyouwang.crud.system.model.resp.SysRoleResp(id,roleName,roleKey,remark) from SysRole where 1=1 " +
            "and (roleName like CONCAT(:#{#sysRole.roleName},'%') or :#{#sysRole.roleName} is null)"+
            "and (roleKey = :#{#sysRole.roleKey} or :#{#sysRole.roleKey} is null or :#{#sysRole.roleKey} = '')",
            countQuery = "from SysRole where 1=1" +
                    " and (roleName like CONCAT(:#{#sysRole.roleName},'%') or :#{#sysRole.roleName} is null)" +
                    " and (roleKey = :#{#sysRole.roleKey} or :#{#sysRole.roleKey} is null or :#{#sysRole.roleKey} = '')")
    Page<SysRoleResp> findPage(@Param("sysRole") SysRoleListReq sysRoleListReq, Pageable pageable);

    /**
     * 根据角色key查询角色
     * @param roleKey 角色key
     * @return 角色信息
     */
    SysRole findByRoleKey(String roleKey);
}
