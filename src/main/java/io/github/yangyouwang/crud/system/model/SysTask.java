package io.github.yangyouwang.crud.system.model;

import io.github.yangyouwang.common.domain.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author yangyouwang
 * @title: SysTask
 * @projectName crud
 * @description: 系统任务
 * @date 2021/4/9下午8:11
 */
@Data
@Entity
@Table(name="sys_task")
public class SysTask extends BaseEntity {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    /**
     * 名称
     */
    @Column(name="name")
    private String name;
    /**
     * cron表达式
     */
    @Column(name="cron")
    private String cron;
    /**
     * 类名称
     */
    @Column(name="class_name")
    private String className;
    /**
     * 方法名
     */
    @Column(name="method_name")
    private String methodName;
    /**
     * 是否启用
     */
    @Column(name="enabled")
    private String enabled;
}
