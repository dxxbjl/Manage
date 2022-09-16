package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysUser
 * @projectName crud
 * @description: 用户类
 * @date 2021/3/2112:22 AM
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 4718572152888798906L;
    /**
     * 账号
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 启用
     */
    private String enabled;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 部门外键
     */
    private Long deptId;
    /**
     * 部门
     */
    @TableField(exist = false)
    private SysDept dept;
    /**
     * 多个用户对应多个角色
     */
    @TableField(exist = false)
    private List<SysRole> roles;
    /**
     * 多个用户对应多个岗位
     */
    @TableField(exist = false)
    private List<SysPost> posts;
    /**
     * 角色ID数组
     */
    @TableField(exist = false)
    private Long[] roleIds;
    /**
     * 岗位ID数组
     */
    @TableField(exist = false)
    private Long[] postIds;
}