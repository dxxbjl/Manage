package io.github.yangyouwang.crud.system.model.resp;

import lombok.Data;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysRoleResp
 * @projectName crud
 * @description: 角色响应
 * @date 2021/3/254:43 PM
 */
@Data
public class SysRoleResp implements Serializable {
    private static final long serialVersionUID = 2757982456198348622L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
    /** 备注 */
    private String remark;
    /**
     * 菜单id
     */
    private Long[] menuIds;
}
