package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskEditReq
 * @projectName crud
 * @description: 编辑任务请求
 * @date 2021/4/10上午10:37
 */
@Data
public class SysTaskEditReq implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 是否启用
     */
    private String enabled;

    /** 备注 */
    private String remark;
}
