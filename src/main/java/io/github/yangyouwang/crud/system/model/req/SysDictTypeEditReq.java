package io.github.yangyouwang.crud.system.model.req;

import io.github.yangyouwang.crud.system.model.dto.SysDictValueDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysDictAddReq
 * @projectName crud
 * @description: 字典编辑请求
 * @date 2021/4/13下午2:27
 */
@Data
public class SysDictTypeEditReq implements Serializable {
    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long id;
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
    private Integer orderNum;
    /**
     * 启用
     */
    @NotBlank(message = "是否启用不能为空")
    private String enabled;

    /** 备注 */
    private String remark;

    /**
     * 字典类型与字典项 一对多
     */
    private List<SysDictValueDto> sysDictValues;
}
