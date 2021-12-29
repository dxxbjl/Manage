package io.github.yangyouwang.crud.system.model.result;

import lombok.Data;

import java.io.Serializable;
/**
 * @author yangyouwang
 * @title: SysDictValueDTO
 * @projectName crud
 * @description: 字典项响应
 * @date 2021/4/13下午2:21
 */
@Data
public class SysDictValueDTO implements Serializable {
    private static final long serialVersionUID = -3914109107568119461L;
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
    /**
     * 备注
     * */
    private String remark;
}
