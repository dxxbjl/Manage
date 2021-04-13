package io.github.yangyouwang.crud.system.model.req;

import io.github.yangyouwang.crud.system.model.SysDictValue;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangyouwang
 * @title: SysDictAddReq
 * @projectName crud
 * @description: 字典添加请求
 * @date 2021/4/13下午2:27
 */
@Data
public class SysDictAddReq implements Serializable {
    /**
     * 类型key
     */
    private String dictKey;
    /**
     * 类型名称
     */
    private String dictName;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 启用
     */
    private String enabled;

    /**
     * 字典类型与字典项 一对多
     */
    private List<SysDictValue> sysDictValues;

    /** 备注 */
    private String remark;
}
