package io.github.yangyouwang.crud.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysRoleEditDTO
 * @projectName crud
 * @description: 角色编辑
 * @date 2021/3/269:56 AM
 */
@Data
public class SysRoleEditDTO implements Serializable {

    private static final long serialVersionUID = -8956402886244571705L;
    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;
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
