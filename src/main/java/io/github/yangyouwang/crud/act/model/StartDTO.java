package io.github.yangyouwang.crud.act.model;

import lombok.Data;

import java.util.List;

/**
 * Description: 发起流程<br/>
 * date: 2022/11/17 23:13<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class StartDTO {
    /**
     * 部署ID
     */
    private String deploymentId;
    /**
     * 表单
     */
    private String flowForm;
    /**
     * 审批人
     */
    private List<String> users;
}
