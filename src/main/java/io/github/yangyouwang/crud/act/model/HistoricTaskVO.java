package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.util.Date;

/**
 * 历史任务
 */
@Data
public class HistoricTaskVO {
    /**
     * 流程ID
     */
    private String id;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 发起人
     */
    private String startUser;
    /**
     * 开始日期
     */
    private Date startTime;
    /**
     * 结束日期
     */
    private Date endTime;
    /**
     * 业务ID
     */
    private String businessKey;
}
