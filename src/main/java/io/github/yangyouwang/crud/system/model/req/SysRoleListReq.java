package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysUserAddReq
 * @projectName crud
 * @description: 用户请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysRoleListReq implements Serializable {
    /**
     * pageNum
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
}
