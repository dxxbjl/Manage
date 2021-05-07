package io.github.yangyouwang.crud.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.yangyouwang.common.domain.BaseEntity;
import io.github.yangyouwang.core.converter.Treeable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysMenu
 * @projectName crud
 * @description: 菜单类
 * @date 2021/3/2112:22 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity implements Treeable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
    private String menuType;
    /**
     * 菜单状态（Y显示 N隐藏）
     */
    private String visible;
    /**
     * 权限标识
     */
    private String perms;

    /** 子菜单 */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    @Override
    public Long getMapKey() {
        return parentId;
    }

    @Override
    public Long getChildrenKey() {
        return id;
    }

    @Override
    public Long getRootKey() {
        return 0L;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
