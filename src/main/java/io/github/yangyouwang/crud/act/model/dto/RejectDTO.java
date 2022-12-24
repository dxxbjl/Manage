package io.github.yangyouwang.crud.act.model.dto;

import lombok.Data;

/**
 * Description: 驳回任务DTO<br/>
 * date: 2022/12/3 19:43<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class RejectDTO {
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 驳回意见
     */
    private String comment;
}
