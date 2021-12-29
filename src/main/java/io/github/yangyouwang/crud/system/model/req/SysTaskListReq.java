package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskListReq
 * @projectName crud
 * @description: 任务列表请求
 * @date 2021/4/10上午10:34
 */
@Data
public class SysTaskListReq implements Serializable {
    private static final long serialVersionUID = -8369361037044697047L;
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
     * 名称
     */
    private String name;
    /**
     * 类名称
     */
    private String className;
}
