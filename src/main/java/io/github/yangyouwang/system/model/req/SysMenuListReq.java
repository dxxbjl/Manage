package io.github.yangyouwang.system.model.req;

import lombok.Data;

import java.io.Serializable;


/**
 * @author yangyouwang
 * @title: SysMenuListReq
 * @projectName crud
 * @description: 菜单请求
 * @date 2021/3/269:56 AM
 */
@Data
public class SysMenuListReq implements Serializable {
    /**
     * pageNum
     */
    private Integer pageNum;
    /**
     * pageSize
     */
    private Integer pageSize;
}
