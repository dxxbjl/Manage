package io.github.yangyouwang.crud.system.model.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangyouwang
 * @title: SysLogResp
 * @projectName crud
 * @description: 日志响应
 * @date 2021/4/111:13 AM
 */
@Data
public class SysLogResp implements Serializable {
    private static final long serialVersionUID = -6756889713225649305L;
    /**
     * 主键id
     */
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
