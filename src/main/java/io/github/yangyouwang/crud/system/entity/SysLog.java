package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

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
@TableName("sys_log")
public class SysLog implements Serializable {
    private static final long serialVersionUID = 5756323850083974770L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 包名称
     */
    private String packageName;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数名
     */
    private String argsName;
    /**
     * 参数值
     */
    private String argsValue;
    /**
     * 异常类型
     */
    private String exceptionName;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 异常堆栈信息
     */
    private String stackTrace;

    /** 创建者 */
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
