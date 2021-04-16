package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskEnabledReq
 * @projectName crud
 * @description: 任务状态
 * @date 2021/4/16上午10:26
 */
@Data
public class SysTaskEnabledReq implements Serializable {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 是否启用
     */
    @NotBlank(message = "是否启用不能为空")
    private String enabled;
}
