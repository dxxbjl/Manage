package io.github.yangyouwang.crud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhixin.yao
 * @version 1.0
 * @description: 数据字典值
 * @date 2021/4/12 11:27
 */
@Data
@TableName("sys_dict_value")
public class SysDictValue {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
