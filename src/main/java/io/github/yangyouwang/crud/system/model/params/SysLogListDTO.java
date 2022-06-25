package io.github.yangyouwang.crud.system.model.params;

import io.github.yangyouwang.common.domain.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysLogListDTO
 * @projectName crud
 * @description: 日志列表对象
 * @date 2021/4/111:09 AM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogListDTO extends PageDTO implements Serializable {
    private static final long serialVersionUID = 5703718482416679645L;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
}
