package io.github.yangyouwang.common.domain;

import lombok.Data;


/**
 * @author yangyouwang
 * @title: XmSelectNode
 * @projectName crud
 * @description: 节点类（必须继承Treeable）
 * @date 2021/3/2910:47 AM
 */
@Data
public class XmSelectNode {
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private Long value;
    /**
     * 是否展示
     */
    private Boolean disabled = false;
    /**
     * 是否选中
     */
    private Boolean selected = false;
}
