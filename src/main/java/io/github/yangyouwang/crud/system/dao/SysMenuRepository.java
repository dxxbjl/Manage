package io.github.yangyouwang.crud.system.dao;

import io.github.yangyouwang.crud.system.model.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author yangyouwang
 * @title: SysMenuRepository
 * @projectName crud
 * @description: 菜单Dao接口
 * @date 2021/3/2112:25 AM
 */
public interface SysMenuRepository extends JpaRepository<SysMenu,Long>,JpaSpecificationExecutor {
    /**
     * 根据菜单类型和状态查询菜单
     * @return 菜单列表
     */
    @Query(nativeQuery = true,value = "select distinct m.id, m.parent_id, m.menu_name, m.url, m.visible, ifnull(m.perms,'') as perms, m.menu_type, m.order_num, m.create_by, m.create_time, m.update_by, m.update_time, m.remark" +
            " from sys_menu m" +
            " where m.menu_type in ('M', 'C') and m.visible = 0" +
            " order by m.parent_id, m.order_num")
    List<SysMenu> findSysMenu();

    /**
     * 根据用户id查询菜单
     */
    @Query(nativeQuery = true,
            value = "select distinct m.id, m.parent_id, m.menu_name, m.url, m.visible, ifnull(m.perms,'') as perms, m.menu_type, m.order_num, m.create_by, m.create_time, m.update_by, m.update_time, m.remark" +
                    " from sys_menu m" +
                    " left join sys_role_menu rm on m.id = rm.menu_id" +
                    " left join sys_user_role ur on rm.role_id = ur.role_id" +
                    " left join sys_role ro on ur.role_id = ro.id" +
                    " where ur.user_id = ?1 and m.menu_type in ('M', 'C') and m.visible = 0" +
                    " order by m.parent_id, m.order_num")
    List<SysMenu> findSysMenuByUserId(Long id);

    /**
     * 根据id查询菜单
     */
    @Query(nativeQuery = true,
            value = "select m.id, m.menu_name, m.parent_id, m.order_num, m.url, m.menu_type, m.visible, m.perms, m.create_by, m.create_time, m.update_by, m.update_time, m.remark ,p.menu_name  as parentName" +
                    " from sys_menu m" +
                    " left join sys_menu p on m.parent_id = p.id" +
                    " where m.id = ?1")
    SysMenu findSysMenuById(Long id);
}
