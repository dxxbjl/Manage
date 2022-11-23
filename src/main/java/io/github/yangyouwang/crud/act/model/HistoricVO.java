package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.util.Date;

/**
 * Description: 审批历史DTO<br/>
 * date: 2022/11/22 23:09<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class HistoricVO {
    /**
     * 节点名称
     */
    private String name;
    /**
     * 审批人
     */
    private String assignee;
    /**
     * 经办时间
     */
    private Date endTime;
    /**
     * 审批意见
     */
    private String comment;
}
