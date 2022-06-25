package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysMenuListDTO
 * @projectName crud
 * @description: 菜单请求
 * @date 2021/3/269:56 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuListDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = -1912383352435987548L;
    /**
     * 菜单名称
     */
    private String menuName;
}
