package io.github.yangyouwang.crud.system.model.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskResp
 * @projectName crud
 * @description: 任务响应
 * @date 2021/4/10上午10:32
 */
@Data
public class SysTaskResp implements Serializable {
    /**
     * 用户id
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
