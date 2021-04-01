package io.github.yangyouwang.crud.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yangyouwang
 * @title: CrudLog
 * @projectName crud
 * @description: 异常日志
 * @date 2021/4/19:50 AM
 */
@Data
@Entity
@Table(name="sys_log")
public class SysLog implements Serializable {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    /**
     * 包名称
     */
    @Column(name="package_name")
    private String packageName;
    /**
     * 类名称
     */
    @Column(name="class_name")
    private String className;
    /**
     * 方法名称
     */
    @Column(name="method_name")
    private String methodName;
    /**
     * 参数名
     */
    @Column(name="args_name")
    private String argsName;
    /**
     * 参数值
     */
    @Column(name="args_value")
    private String argsValue;
    /**
     * 异常类型
     */
    @Column(name="exception_name")
    private String exceptionName;
    /**
     * 错误信息
     */
    @Column(name="err_msg")
    private String errMsg;
    /**
     * 异常堆栈信息
     */
    @Column(name="stack_trace")
    private String stackTrace;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;
}
