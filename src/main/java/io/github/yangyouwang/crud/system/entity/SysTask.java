package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;


/**
 * @author yangyouwang
 * @title: SysTask
 * @projectName crud
 * @description: 系统任务
 * @date 2021/4/9下午8:11
 */
@Data
@TableName("sys_task")
public class SysTask extends BaseEntity {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
}
