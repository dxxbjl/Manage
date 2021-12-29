package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysRoleListDTO
 * @projectName crud
 * @description: 用户请求
 * @date 2021/3/269:56 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleListDTO extends PageDto implements Serializable {
    private static final long serialVersionUID = -650252266859884454L;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色标识
     */
    private String roleKey;
}
