package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yangyouwang
 * @title: SysDictTypeAddDTO
 * @projectName crud
 * @description: 字典添加请求
 * @date 2021/4/13下午2:27
 */
@Data
public class SysDictTypeAddDTO extends BaseEntity {
    private static final long serialVersionUID = 2436852390461848216L;
    /**
     * 类型key
     */
    @NotBlank(message = "类型别名不能为空")
    private String dictKey;
    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    private String dictName;
    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;
    /**
     * 启用
     */
    @NotBlank(message = "是否启用不能为空")
    private String enabled;
}
