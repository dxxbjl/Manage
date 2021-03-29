package io.github.yangyouwang.system.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysMenuResp
 * @projectName crud
 * @description: 菜单响应
 * @date 2021/3/254:43 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuResp implements Serializable {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;
    /**
     * 菜单状态（Y显示 N隐藏）
     */
    private String visible;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 备注
     */
    private String remark;
    /**
     * 父菜单名称
     */
    private String parentName;
}
