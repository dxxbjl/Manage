package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.util.Date;

/**
 * 任务VO
 */
@Data
public class TaskVO {
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
     * 代理人
     */
    private String assignee;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 创建日期
     */
    private Date createTime;
}
