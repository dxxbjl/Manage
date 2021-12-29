package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysTaskAddReq
 * @projectName crud
 * @description: 添加任务请求
 * @date 2021/4/10上午10:35
 */
@Data
public class SysTaskAddReq implements Serializable {

    private static final long serialVersionUID = -5068908296247542518L;
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

    /** 备注 */
    private String remark;
}
