package io.github.yangyouwang.crud.system.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskEnabledDTO
 * @projectName crud
 * @description: 任务状态
 * @date 2021/4/16上午10:26
 */
@Data
public class SysTaskEnabledDTO implements Serializable {
    private static final long serialVersionUID = 3096251015899859465L;
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
