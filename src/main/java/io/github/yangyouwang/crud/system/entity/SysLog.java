package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
