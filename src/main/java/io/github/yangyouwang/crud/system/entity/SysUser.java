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
     * 部门外键
     */
    private Long deptId;
    /**
     * 昵称
     */
    private String nickName;
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
     * 角色ID数组
     */
    @TableField(exist = false)
    private String roleIds;
    /**
     * 岗位ID数组
     */
    @TableField(exist = false)
    private String postIds;
    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 岗位名称
     */
    @TableField(exist = false)
    private String postName;
    /**
     * 根据部门ids模糊查询用户
     */
    @TableField(exist = false)
    private List<Object> deptIds;
}