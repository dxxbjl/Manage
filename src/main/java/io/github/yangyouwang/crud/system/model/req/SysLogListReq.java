package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysLogListReq
 * @projectName crud
 * @description: 系统日期请求
 * @date 2021/4/111:09 AM
 */
@Data
public class SysLogListReq implements Serializable {
    /**
     * pageNum
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
}
