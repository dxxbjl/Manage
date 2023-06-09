package io.github.yangyouwang.module.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

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
@ApiModel(value="SysLog对象", description="异常日志表")
public class SysLog {
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
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
