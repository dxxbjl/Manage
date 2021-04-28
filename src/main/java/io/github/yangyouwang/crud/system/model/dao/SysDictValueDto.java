package io.github.yangyouwang.crud.system.model.dao;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangyouwang
 * @title: SysDictValueDto
 * @projectName crud
 * @description: 字典项
 * @date 2021/4/15下午7:04
 */
@Data
public class SysDictValueDto implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 字典类型id
     */
    private Long dictTypeId;
    /**
     * 字典值key
     */
    private String dictValueKey;

    /**
     * 字典值名称
     */
    private String dictValueName;
    /**
     * 显示顺序
     */
    private Integer orderNum;
    /** 备注 */
    private String remark;
}
