package io.github.yangyouwang.common.domain;

import io.github.yangyouwang.core.converter.Treeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yangyouwang
 * @title: TreeNode
 * @projectName crud
 * @description: 节点类（必须继承Treeable）
 * @date 2021/3/2910:47 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode implements Treeable {
    /**
     * id
     */
    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否选中
     */
    private Boolean checked;
    /**
     * 是否打开
     */
    private Boolean open;
    /**
     * 节点
     */
    private List<TreeNode> children;


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
