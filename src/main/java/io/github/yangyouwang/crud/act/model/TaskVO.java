package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.util.Date;

/**
 * 任务VO
 */
@Data
public class TaskVO {
    /**
     * 任务ID
     */
    private String id;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 流程名称
     */
    private String flowName;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 代理人
     */
    private String assignee;
    /**
     * 创建日期
     */
    private Date createTime;
}
