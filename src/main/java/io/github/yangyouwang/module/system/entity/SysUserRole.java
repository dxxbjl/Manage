package io.github.yangyouwang.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户关联角色表
 * sys_user_role
 * @author yangyouwang
 */
@Data
@TableName("sys_user_role")
@ApiModel(value="SysUserRole对象", description="用户关联角色表")
public class SysUserRole {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;
}
