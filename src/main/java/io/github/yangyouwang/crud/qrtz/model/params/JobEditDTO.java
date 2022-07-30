package io.github.yangyouwang.crud.qrtz.model.params;

import lombok.Data;

/**
 * Description: 任务详细信息修改<br/>
 * date: 2022/7/29 22:39<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class JobEditDTO {
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
