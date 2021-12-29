package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysMenuListReq
 * @projectName crud
 * @description: 菜单请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysMenuListReq implements Serializable {
    private static final long serialVersionUID = -1912383352435987548L;
    /**
     * 菜单名称
     */
    private String menuName;
}
