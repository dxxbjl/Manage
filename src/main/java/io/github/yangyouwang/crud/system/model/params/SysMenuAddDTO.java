package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author yangyouwang
 * @title: SysMenuAddDTO
 * @projectName crud
 * @description: 菜单请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysMenuAddDTO extends BaseEntity {
    private static final long serialVersionUID = -6556795416011953985L;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    /**
     * 父菜单ID
     */
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;
    /**
     * 图标
     */
    private String icon;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;
    /**
     * 菜单状态（Y显示 N隐藏）
     */
    @NotBlank(message = "菜单状态不能为空")
    private String visible;
    /**
     * 权限标识
     */
    private String perms;
}
