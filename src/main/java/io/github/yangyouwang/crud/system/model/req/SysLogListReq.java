package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysLogListReq
 * @projectName crud
 * @description: 日志列表对象
 * @date 2021/4/111:09 AM
 */
@Data
public class SysLogListReq implements Serializable {
    private static final long serialVersionUID = 5703718482416679645L;
    /**
     * pageNum
     */
    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    /**
     * pageSize
     */
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
}
