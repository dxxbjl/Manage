package io.github.yangyouwang.system.dao;

import io.github.yangyouwang.system.model.SysUser;
import io.github.yangyouwang.system.model.resp.SysUserResp;
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
     * @param userName 查询条件
     * @return 用户列表
     */
    @Query(value = "select new io.github.yangyouwang.system.model.resp.SysUserResp(id,userName,enabled,email,phonenumber,sex,avatar) from SysUser where 1=1 and (userName like CONCAT('%',?1,'%') or ?1 is null)",
            countQuery = "from SysUser u where 1=1 and (userName like CONCAT('%',?1,'%') or ?1 is null)")
    Page<SysUserResp> findPage(@Param("userName") String userName, Pageable pageable);
}
