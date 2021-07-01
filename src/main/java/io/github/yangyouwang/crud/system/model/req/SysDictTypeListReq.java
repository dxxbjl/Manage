package io.github.yangyouwang.crud.system.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysDictListReq
 * @projectName crud
 * @description: 字典列表请求
 * @date 2021/4/13下午1:37
 */
@Data
public class SysDictTypeListReq implements Serializable {
    /**
     * pageNum
     */
    @NotNull(message = "pageNum不能为空")
    private Integer pageNum;
    /**
     * pageSize
     */
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典key
     */
    private String dictKey;
}
