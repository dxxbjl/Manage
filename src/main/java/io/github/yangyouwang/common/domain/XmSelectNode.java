package io.github.yangyouwang.common.domain;

import io.github.yangyouwang.core.converter.Treeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yangyouwang
 * @title: TreeSelectNode
 * @projectName crud
 * @description: 节点类（必须继承Treeable）
 * @date 2021/3/2910:47 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XmSelectNode implements Treeable {

    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private Long value;
    /**
     * id
     */
    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 是否展示
     */
    private Boolean disabled = false;
    /**
     * 是否选中
     */
    private Boolean selected = false;
    /**
     * 节点
     */
    private List<XmSelectNode> children;


    @Override
    public Long getMapKey() {
        return parentId;
    }

    @Override
    public Long getChildrenKey() {
        return value;
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
