package io.github.yangyouwang.crud.qrtz.model.params;

import io.github.yangyouwang.common.domain.PageDTO;
import lombok.Data;

/**
 * Description: 任务分页<br/>
 * date: 2022/7/29 22:26<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class JobPageDTO extends PageDTO {
    /**
     * 工作名字
     */
    private String jobName;
}
