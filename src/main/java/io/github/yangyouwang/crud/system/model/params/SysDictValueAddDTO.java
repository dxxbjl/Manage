package io.github.yangyouwang.crud.system.model.params;
import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yangyouwang
 * @title: SysDictValueAddDTO
 * @projectName crud
 * @description: 字典添加请求
 * @date 2021/4/13下午2:27
 */
@Data
public class SysDictValueAddDTO extends BaseEntity {
    private static final long serialVersionUID = -7563787018756901L;
    /**
     * 字典类型id
     */
    @NotNull(message = "字典类型id不能为空")
    private Long dictTypeId;
    /**
     * 字典值key
     */
    @NotBlank(message = "字典值key不能为空")
    private String dictValueKey;

    /**
     * 字典值名称
     */
    @NotBlank(message = "字典值名称不能为空")
    private String dictValueName;
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
