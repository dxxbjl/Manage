package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysRoleAddReq
 * @projectName crud
 * @description: 角色请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysRoleAddReq implements Serializable {
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    /**
     * 角色标识
     */
    @NotBlank(message = "角色标识不能为空")
    private String roleKey;
    /** 备注 */
    private String remark;
    /**
     * 菜单
     */
    @NotEmpty(message = "菜单id不能为空")
    private Long[] menuIds;
}
