package io.github.yangyouwang.crud.act.model;

import lombok.Data;

/**
 * 流程 VO
 */
@Data
public class FlowVO {
    /**
     * 流程ID
     */
    private String id;
    /**
     * 流程分类
     */
    private String category;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 流程key
     */
    private String key;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 流程版本
     */
    private int version;
    /**
     * 部署ID
     */
    private String deploymentId;
}
