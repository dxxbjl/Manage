package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * @author yangyouwang
 * @title: SysTaskEditDTO
 * @projectName crud
 * @description: 编辑任务请求
 * @date 2021/4/10上午10:37
 */
@Data
public class SysTaskEditDTO extends BaseEntity {
    private static final long serialVersionUID = -8653724349109395318L;
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String name;
    /**
     * cron表达式
     */
    @NotBlank(message = "cron表达式不能为空")
    private String cron;
    /**
     * 类名称
     */
    @NotBlank(message = "类名称不能为空")
    private String className;
    /**
     * 方法名
     */
    @NotBlank(message = "方法名不能为空")
    private String methodName;
    /**
     * 是否启用
     */
    @NotBlank(message = "是否启用不能为空")
    private String enabled;
}
