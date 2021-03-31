package io.github.yangyouwang.system.model;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysRole
 * @projectName crud
 * @description: 角色类
 * @date 2021/3/2112:22 AM
 */
@Data
@Entity
@Table(name="sys_role")
public class SysRole extends BaseEntity {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    /**
     * 角色名称
     */
    @Column(name="role_name")
    private String roleName;
    /**
     * 角色标识
     */
    @Column(name="role_key")
    private String roleKey;

    /**
     * 关联用户
     */
    @ManyToMany(mappedBy = "roles")
    private List<SysUser> users;

    /**
     * 多个角色对应多个菜单
     */
    @ManyToMany(targetEntity = SysMenu.class,fetch = FetchType.EAGER)
    @JoinTable(name="sys_role_menu",
            joinColumns={@JoinColumn(name="role_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="menu_id",referencedColumnName="id")}
    )
    private List<SysMenu> menus;
}
