package io.github.yangyouwang.system.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysRoleResp
 * @projectName crud
 * @description: 角色响应
 * @date 2021/3/254:43 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleResp implements Serializable {

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
}
