package io.github.yangyouwang.crud.act.model;

import lombok.Data;

/**
 * 完成任务
 */
@Data
public class CompleteDTO {
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 审批人
     */
    private String assignee;
    /**
     * 审批决策
     */
    private String pass;
    /**
     * 审批意见
     */
    private String comment;
}
