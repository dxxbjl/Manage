package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysUser;
import io.github.yangyouwang.crud.system.model.req.SysUserListReq;
import io.github.yangyouwang.crud.system.model.resp.SysUserResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author yangyouwang
 * @title: SysUserRepository
 * @projectName crud
 * @description: 用户Dao接口
 * @date 2021/3/2112:25 AM
 */
public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    SysUser findByUserName(String userName);

    /**
     * 查询分页数据
     * @param sysUserListReq 查询条件
     * @param pageable pageable
     * @return 用户列表
     */
    @Query(value = "select new io.github.yangyouwang.crud.system.model.resp.SysUserResp(id,userName,enabled,email,phonenumber,sex,avatar,remark) from SysUser where 1=1 " +
            "and (userName like CONCAT(:#{#sysUser.userName},'%') or :#{#sysUser.userName} is null)"+
            "and (phonenumber =:#{#sysUser.phonenumber} or :#{#sysUser.phonenumber} is null or :#{#sysUser.phonenumber} = '')"+
            "and (email = :#{#sysUser.email} or :#{#sysUser.email} is null or :#{#sysUser.email} = '')",
            countQuery = "from SysUser where 1=1" +
                    " and (userName like CONCAT(:#{#sysUser.userName},'%') or :#{#sysUser.userName} is null)" +
                    " and (phonenumber =:#{#sysUser.phonenumber} or :#{#sysUser.phonenumber} is null or :#{#sysUser.phonenumber} = '')"+
                    " and (email = :#{#sysUser.email} or :#{#sysUser.email} is null or :#{#sysUser.email} = '')")
    Page<SysUserResp> findPage(@Param("sysUser") SysUserListReq sysUserListReq, Pageable pageable);
}
