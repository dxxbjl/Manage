package io.github.yangyouwang.crud.qrtz.model.result;

import lombok.Data;

/**
 * Description: 任务详细信息返回<br/>
 * date: 2022/7/30 15:34<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class JobDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 任务名字
     */
    private String jobName;
    /**
     * 工作类名字
     */
    private String jobClassName;
    /**
     * 表达式
     */
    private String cron;
    /**
     * 备注
     */
    private String remark;
}
