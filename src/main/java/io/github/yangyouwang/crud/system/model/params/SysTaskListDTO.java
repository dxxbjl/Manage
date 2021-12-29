package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskListDTO
 * @projectName crud
 * @description: 任务列表请求
 * @date 2021/4/10上午10:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTaskListDTO extends PageDto implements Serializable {
    private static final long serialVersionUID = -8369361037044697047L;
    /**
     * 名称
     */
    private String name;
    /**
     * 类名称
     */
    private String className;
}
